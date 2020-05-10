import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.adarshr.gradle.testlogger.theme.ThemeType.MOCHA
import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import java.util.*

plugins {
    kotlin("jvm") version "1.3.72"
    `maven-publish`
    id("com.jfrog.bintray") version "1.8.5"
    id("io.qameta.allure") version "2.8.1"
    id("net.vivin.gradle-semantic-build-versioning") apply false
    id("com.adarshr.test-logger") version "2.0.0" apply true
    id("com.github.ben-manes.versions") version "0.28.0"
}

group = properties["GROUP"].toString()

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("com.fasterxml.jackson.core:jackson-databind:2.11.0")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.11.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.11.0")
    implementation("com.github.mifmif:generex:1.0.2")
    implementation("io.github.classgraph:classgraph:4.8.78")
    implementation("org.slf4j:slf4j-api:1.7.30")
    testImplementation("io.kotest:kotest-runner-junit5:4.0.5")
    testImplementation("io.kotest:kotest-extensions-allure:4.0.5")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:4.0.5")
    testImplementation("io.kotest:kotest-assertions-core-jvm:4.0.5")
    testImplementation("io.kotest:kotest-property-jvm:4.0.5")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.2")
    testRuntimeOnly("ch.qos.logback:logback-core:1.2.3")
    testRuntimeOnly("ch.qos.logback:logback-classic:1.2.3")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.2")
    testRuntimeOnly("org.codehaus.groovy:groovy:3.0.3")
    runtimeOnly(kotlin("script-runtime"))
}

tasks {
    jar {
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
}

val compileKotlin by tasks.getting(KotlinCompile::class) {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

val compileTestKotlin by tasks.getting(KotlinCompile::class) {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<Wrapper> {
    this.gradleVersion = "6.4"
    this.distributionType = Wrapper.DistributionType.ALL
}

val test by tasks.getting(Test::class) {
    useJUnitPlatform {}

    // show standard out and standard error of the test JVM(s) on the console
    testLogging.showStandardStreams = true

    // Always run tests, even when nothing changed.
    dependsOn("cleanTest")

    testLogging {
        showStandardStreams = true
        events(
            TestLogEvent.FAILED,
            TestLogEvent.SKIPPED,
            TestLogEvent.STANDARD_OUT,
            TestLogEvent.STANDARD_ERROR
        )
        exceptionFormat = TestExceptionFormat.FULL
        showExceptions = true
        showCauses = true
        showStackTraces = true

        debug {
            events(
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

testlogger {
    showPassed = false
    theme = MOCHA
}

allure {
    version = "2.8.1"
    aspectjweaver = false
    aspectjVersion = "1.9.5"
    autoconfigure = true
    allureJavaVersion = "2.13.3"
    useJUnit5 {
        version = "2.13.3"
    }
}

tasks.withType<DependencyUpdatesTask> {
    fun isNonStable(version: String): Boolean {
        val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
        val regex = "^[0-9,.v-]+(-r|-jre)?$".toRegex()
        val isStable = stableKeyword || regex.matches(version)
        return isStable.not()
    }

    rejectVersionIf {
        isNonStable(candidate.version)
    }
}

val sourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.getByName("main").allSource)
    from("LICENCE.md") {
        into("META-INF")
    }
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
    user = project.findProperty("bintrayUser").toString()
    key = project.findProperty("bintrayKey").toString()
    publish = !project.version.toString().endsWith("SNAPSHOT")

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
            vcsTag = "v$artifactVersion"
        }
    }
}
