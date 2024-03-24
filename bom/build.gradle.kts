plugins {
    `java-platform`
    `maven-publish`
    signing
}

val bom = project

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
                    api("${publication.groupId}:${publication.artifactId}:${publication.version}")
                }
            }
        }
    }
}

/**
 * For additional providers, use a combination of rootProject and subproject names for artifact name and similar things.
 * i.e. kotlin-faker-books, kotlin-faker-movies, kotlin-faker-tv, ...
 *
 * The "core" lib retains the same name as before: kotlin-faker
 */
private val fullName: String =
    if (project.name == "core") rootProject.name else "${rootProject.name}-${project.name}"

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
    if (!version.toString().endsWith("SNAPSHOT")) {
        sign(publishing.publications["FakerBom"])
    }
}
