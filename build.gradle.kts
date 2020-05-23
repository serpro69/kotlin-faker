import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
//    `kotlin-dsl` version "1.3.6" apply false
    kotlin("jvm") version "1.3.72" apply false
    id("net.vivin.gradle-semantic-build-versioning") apply false
    id("com.adarshr.test-logger") version "2.0.0" apply false
    id("com.github.ben-manes.versions") version "0.28.0" apply false
    id("io.qameta.allure") version "2.8.1" apply false
}

subprojects {
    group = properties["GROUP"].toString()

    repositories {
        jcenter()
        mavenCentral()
    }

    apply {
//        plugin("org.gradle.kotlin.kotlin-dsl")
        plugin("java")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("com.adarshr.test-logger")
        plugin("com.github.ben-manes.versions")
        plugin("io.qameta.allure")
    }

    dependencies {
        val implementation by configurations
        val testRuntimeOnly by configurations
        val testImplementation by configurations

        implementation(kotlin("stdlib-jdk8"))
        implementation(kotlin("reflect"))
        implementation("org.slf4j:slf4j-api:1.7.30")
        testImplementation("io.kotest:kotest-runner-junit5:4.0.5")
        testImplementation("io.kotest:kotest-extensions-allure:4.0.5")
        testImplementation("io.kotest:kotest-runner-junit5-jvm:4.0.5")
        testImplementation("io.kotest:kotest-assertions-core-jvm:4.0.5")
        testImplementation("io.kotest:kotest-property-jvm:4.0.5")
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.2")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.2")
        testRuntimeOnly("ch.qos.logback:logback-core:1.2.3")
        testRuntimeOnly("ch.qos.logback:logback-classic:1.2.3")
        testRuntimeOnly("org.codehaus.groovy:groovy:3.0.3")
    }

    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        sourceCompatibility = "1.8"
        targetCompatibility = "1.8"
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform {}

        // show standard out and standard error of the test JVM(s) on the console
        testLogging.showStandardStreams = true

        // Always run tests, even when nothing changed.
        dependsOn("cleanTest")

        testLogging {
            showStandardStreams = true
            events(
                org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED,
                org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED,
                org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_OUT,
                org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_ERROR
            )
            exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
            showExceptions = true
            showCauses = true
            showStackTraces = true

            debug {
                events(
                    org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED,
                    org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED,
                    org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_OUT,
                    org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_ERROR
                )
                exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
            }

            info.events = debug.events
            info.exceptionFormat = debug.exceptionFormat
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
}
