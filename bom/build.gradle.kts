import io.github.serpro69.semverkt.gradle.plugin.tasks.TagTask
import kotlinx.validation.KotlinApiBuildTask
import kotlinx.validation.KotlinApiCompareTask

plugins {
    `java-platform`
    `maven-publish`
    signing
}

val bom = project

val isDev = provider { version.toString().startsWith("0.0.0") }
val isSnapshot = provider {
    // QUESTION do we need to check if rootProject is also set to snapshot?
    //  Likely not, since "isRelease" will not just check for the version, but also for the actual tag creation
    //    rootProject.version.toString().endsWith("SNAPSHOT") &&
    version.toString().endsWith("SNAPSHOT")
}
val isRelease = provider {
    val tag = project.tasks.getByName("tag", TagTask::class)
    /* all fakers have their own tags, so checking if tag.didWork is enough for them,
       ':core' shares the tag with 'root', ':bom' and ':cli-bot' modules,
       and hence the tag might already exist and didWork will return false for ':core' */
    val tagCreated = if (project.name != "core") tag.didWork else tag.didWork || tag.tagExists
    !isDev.get() && !isSnapshot.get() && tagCreated
}

// Exclude subprojects that will never be published so that when configuring this project
// we don't force their configuration and do unnecessary work
val excludeFromBom = listOf("test")
fun projectsFilter(candidateProject: Project) =
    excludeFromBom.all { !candidateProject.name.contains(it) } &&
        candidateProject.name != bom.name

// Declare that this subproject depends on all subprojects matching the filter
// When this subproject is configured, it will force configuration of all subprojects
// so that we can declare dependencies on them
rootProject.subprojects.filter(::projectsFilter).forEach { bom.evaluationDependsOn(it.path) }

dependencies {
    constraints {
        rootProject.subprojects.filter { project ->
            // Only declare dependencies on projects that will have publications
            projectsFilter(project) && project.tasks.findByName("publish")?.enabled == true
        }.forEach { project ->
            project.publishing.publications.forEach { publication: Publication ->
                if (publication is MavenPublication) {
                    // use publication coordinates rather than project because they could differ
                    api("${publication.groupId}:${publication.artifactId}:${publication.version}")
                }
            }
        }
    }
}

/**
 * For additional modules, we use a combination of rootProject and subproject names for artifact name and similar things,
 * i.e. kotlin-faker-bom
 */
private val fullName: String = "${rootProject.name}-${project.name}"

publishing {
    publications {
        create<MavenPublication>("FakerBom") {
            from(components["javaPlatform"])
            groupId = project.group.toString()
            artifactId = fullName
            version = project.version.toString()
            pom {
                packaging = "pom"
                name.set("kotlin-faker")
                description.set("Generate realistically looking fake data such as names, addresses, banking details, and many more, that can be used for testing and data anonymization purposes.")
                url.set("https://github.com/serpro69/kotlin-faker")
                scm {
                    connection.set("scm:git:https://github.com/serpro69/kotlin-faker")
                    developerConnection.set("scm:git:https://github.com/serpro69")
                    url.set("https://github.com/serpro69/kotlin-faker")
                }
                issueManagement {
                    url.set("https://github.com/serpro69/kotlin-faker/issues")
                }
                licenses {
                    license {
                        name.set("MIT")
                        url.set("https://opensource.org/licenses/mit-license.php")
                    }
                }
                developers {
                    developer {
                        id.set("serpro69")
                        name.set("Serhii Prodan")
                    }
                }
            }
        }
    }
}

signing {
    sign(publishing.publications["FakerBom"])
}

/*
 * copy from faker-lib-conventions.gradle.kts:219
 */
tasks.withType<PublishToMavenRepository>().configureEach {
    dependsOn(project.tasks.getByName("tag"))
    dependsOn(project.tasks.withType(Sign::class.java))
    onlyIf("Not dev") { !isDev.get() }
    onlyIf("Release or snapshot") { isRelease.get() || isSnapshot.get() }
}

/*
 * copy from faker-lib-conventions.gradle.kts:226
 */
tasks.withType<PublishToMavenLocal>().configureEach {
    onlyIf("In development") { isDev.get() || isSnapshot.get() }
}

/*
 * copy from faker-lib-conventions.gradle.kts:230
 */
tasks.withType<Sign>().configureEach {
    dependsOn(project.tasks.getByName("tag"))
    onlyIf("Not dev and snapshot") { !isDev.get() && !isSnapshot.get() }
    onlyIf("Release") { isRelease.get() }
}
