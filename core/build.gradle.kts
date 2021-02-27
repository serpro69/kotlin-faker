import java.util.*

plugins {
    kotlin("jvm")
    `maven-publish`
    id("com.jfrog.bintray") version "1.8.5"
    id("org.jetbrains.dokka") version "1.4.20"
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.12.1")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.12.1")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.1")
    implementation("com.github.mifmif:generex:1.0.2")
    runtimeOnly(kotlin("script-runtime"))
}

tasks.withType<Jar> {
    archiveBaseName.set(rootProject.name)

    manifest {
        attributes(
            mapOf(
                "Implementation-Title" to project.name,
                "Implementation-Version" to project.version,
                "Class-Path" to configurations.compileClasspath.get().joinToString(" ") { it.name }
            )
        )
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

testlogger {
    showPassed = false
    theme = com.adarshr.gradle.testlogger.theme.ThemeType.MOCHA
}

val sourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.getByName("main").allSource)
    from("LICENCE.md") {
        into("META-INF")
    }
}

val dokkaJavadocJar by tasks.creating(Jar::class) {
    dependsOn(tasks.dokkaJavadoc)
    from(tasks.dokkaJavadoc.get().outputDirectory.orNull)
    archiveClassifier.set("javadoc")
}

val artifactName = rootProject.name
val artifactGroup = project.group.toString()
val artifactVersion = project.version.toString()
val releaseTagName = "v$artifactVersion"

val pomUrl = "https://github.com/serpro69/kotlin-faker"
val pomScmUrl = "https://github.com/serpro69/kotlin-faker"
val pomIssueUrl = "https://github.com/serpro69/kotlin-faker/issues"
val pomDesc = "https://github.com/serpro69/kotlin-faker"

val ghRepo = "serpro69/kotlin-faker"
val ghReadme = "README.md"

val pomLicenseName = "MIT"
val pomLicenseUrl = "https://opensource.org/licenses/mit-license.php"
val pomLicenseDist = "repo"

val pomDeveloperId = "serpro69"
val pomDeveloperName = "Sergii Prodanov"


publishing {
    publications {
        create<MavenPublication>("kotlin-faker") {
            groupId = artifactGroup
            artifactId = artifactName
            version = artifactVersion
            from(components["java"])
            artifact(sourcesJar)
            artifact(dokkaJavadocJar) //TODO configure dokka or use defaults?

            pom {
                packaging = "jar"
                name.set(rootProject.name)
                description.set(pomDesc)
                url.set(pomUrl)
                scm {
                    url.set(pomScmUrl)
                }
                issueManagement {
                    url.set(pomIssueUrl)
                }
                licenses {
                    license {
                        name.set(pomLicenseName)
                        url.set(pomLicenseUrl)
                    }
                }
                developers {
                    developer {
                        id.set(pomDeveloperId)
                        name.set(pomDeveloperName)
                    }
                }
            }
        }
    }
}

bintray {
    val baseReleasePattern = "v\\d+\\.\\d+\\.\\d+"

    user = project.findProperty("bintrayUser").toString()
    key = project.findProperty("bintrayKey").toString()
    publish = releaseTagName.matches(Regex("^${baseReleasePattern}(-rc\\.\\d+)?$"))

    setPublications("kotlin-faker")

    pkg.apply {
        val isRelease = releaseTagName.matches(Regex("^${baseReleasePattern}$"))

        repo = if (isRelease) "maven" else "maven-release-candidates"
        name = artifactName
        userOrg = "serpro69"
        githubRepo = ghRepo
        vcsUrl = "$pomScmUrl.git"
        description = "Port of ruby faker gem written in kotlin"
        setLabels("kotlin", "faker", "testing", "test-automation", "data", "generation")
        setLicenses("MIT")
        desc = description
        websiteUrl = pomUrl
        issueTrackerUrl = pomIssueUrl
        githubReleaseNotesFile = ghReadme

        version.apply {
            name = artifactVersion
            desc = pomDesc
            released = Date().toString()
            vcsTag = releaseTagName
        }
    }
}
