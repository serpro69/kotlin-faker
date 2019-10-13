import org.gradle.api.tasks.testing.logging.*
import org.jetbrains.kotlin.gradle.tasks.*
import java.util.*

plugins {
    kotlin("jvm") version "1.3.21"
    `build-scan`
    `maven-publish`
    id("com.jfrog.bintray") version "1.8.4"
    id("io.qameta.allure") version "2.8.1"
}

group = properties["GROUP"].toString()
version = properties["VERSION"].toString()

repositories {
    jcenter()
    mavenCentral()
    maven("https://dl.bintray.com/serpro69/maven")
}

val agent: Configuration by configurations.creating

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("org.codehaus.groovy:groovy:2.5.6")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.9.8")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.9.8")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.8")
    implementation("com.github.mifmif:generex:1.0.2")
    implementation("ch.qos.logback:logback-core:1.2.3")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("io.github.classgraph:classgraph:4.8.24")
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.2.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.5.2")
    testImplementation("io.kotlintest:kotlintest-extensions-allure:3.2.1")
    runtime(kotlin("script-runtime"))
    implementation("io.github.serpro69:aspe.Kt-core:0.1")
    agent("org.aspectj:aspectjweaver:1.9.4")
}

buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"
}

tasks {
    jar {
        manifest {
            attributes(
                mapOf(
                    "Implementation-Title" to project.name,
                    "Implementation-Version" to project.version,
                    "Class-Path" to configurations.compile.get().joinToString(" ") { it.name }
                )
            )
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Wrapper> {
    this.gradleVersion = "5.2.1"
    this.distributionType = Wrapper.DistributionType.ALL
}

val test by tasks.getting(Test::class) {
    useJUnitPlatform {}
    doFirst {
        jvmArgs("-javaagent:${agent.singleFile}")
    }

    // show standard out and standard error of the test JVM(s) on the console
    testLogging.showStandardStreams = true

    // Always run tests, even when nothing changed.
    dependsOn("cleanTest")

    testLogging {
        showStandardStreams = true
        events(
            TestLogEvent.STARTED,
            TestLogEvent.PASSED,
            TestLogEvent.FAILED,
            TestLogEvent.SKIPPED,
            TestLogEvent.STANDARD_OUT
        )
        exceptionFormat = TestExceptionFormat.FULL
        showExceptions = true
        showCauses = true
        showStackTraces = true

        debug {
            events(
                TestLogEvent.STARTED,
                TestLogEvent.PASSED,
                TestLogEvent.FAILED,
                TestLogEvent.SKIPPED,
                TestLogEvent.STANDARD_OUT,
                TestLogEvent.STANDARD_ERROR
            )
            exceptionFormat = TestExceptionFormat.FULL
        }

        info.events = debug.events
        info.exceptionFormat = debug.exceptionFormat
    }
}

allure {
    version = "2.8.1"
    aspectjweaver = false
    autoconfigure = true
    allureJavaVersion = "2.12.1"
    useJUnit5 {
        version = "2.12.1"
    }
}

val sourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.getByName("main").allSource)
}

val artifactName = project.name
val artifactGroup = project.group.toString()
val artifactVersion = project.version.toString()

val pomUrl = "https://github.com/serpro69/kotlin-faker"
val pomScmUrl = "https://github.com/serpro69/kotlin-faker"
val pomIssueUrl = "https://github.com/serpro69/kotlin-faker/issues"
val pomDesc = "https://github.com/serpro69/kotlin-faker"

val githubRepo = "serpro69/kotlin-faker"
val githubReadme = "README.md"

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

            pom.withXml {
                asNode().apply {
                    appendNode("description", pomDesc)
                    appendNode("name", rootProject.name)
                    appendNode("url", pomUrl)
                    appendNode("licenses").appendNode("license").apply {
                        appendNode("name", pomLicenseName)
                        appendNode("url", pomLicenseUrl)
                        appendNode("distribution", pomLicenseDist)
                    }
                    appendNode("developers").appendNode("developer").apply {
                        appendNode("id", pomDeveloperId)
                        appendNode("name", pomDeveloperName)
                    }
                    appendNode("scm").apply {
                        appendNode("url", pomScmUrl)
                    }
                }
            }
        }
    }
}

bintray {
    user = project.findProperty("bintrayUser").toString()
    key = project.findProperty("bintrayKey").toString()
    publish = true

    setPublications("kotlin-faker")

    pkg.apply {
        repo = "maven"
        name = artifactName
        userOrg = "serpro69"
        githubRepo = githubRepo
        vcsUrl = pomScmUrl
        description = "Port of ruby faker gem written in kotlin"
        setLabels("kotlin", "faker", "testing", "test-automation", "data", "generation")
        setLicenses("MIT")
        desc = description
        websiteUrl = pomUrl
        issueTrackerUrl = pomIssueUrl
        githubReleaseNotesFile = githubReadme

        version.apply {
            name = artifactVersion
            desc = pomDesc
            released = Date().toString()
            vcsTag = artifactVersion
        }
    }
}
