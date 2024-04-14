import com.google.devtools.ksp.gradle.KspTaskJvm
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    kotlin("jvm")
    id("com.google.devtools.ksp") version "1.9.21-1.0.15"
}

group = "io.github.serpro69"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(libs.kotlin.stdlib.jdk8)
    testImplementation(libs.ksp)
    testImplementation(projects.core)
    testImplementation(projects.faker.books)
    testImplementation(projects.faker.edu)
    testImplementation(projects.extension.kotestProperty)
    testImplementation(libs.bundles.test.kotest)
    kspTest(projects.extension.kotestProperty)
    kspTest(projects.extension.kotestPropertyKsp)
    kspTest(projects.core)
    kspTest(projects.faker.books)
    kspTest(projects.faker.edu)
}

tasks.test {
    dependsOn(":core:shadowJar")
    dependsOn(":faker:books:shadowJar")
    dependsOn(":faker:edu:shadowJar")
    testLogging {
        // set options for log level LIFECYCLE
        events =
            setOf(
                TestLogEvent.FAILED,
                TestLogEvent.SKIPPED,
                TestLogEvent.STANDARD_OUT,
            )
        exceptionFormat = TestExceptionFormat.FULL
        showExceptions = true
        showCauses = true
        showStackTraces = true
        // set options for log level DEBUG and INFO
        debug {
            events =
                setOf(
                    TestLogEvent.STARTED,
                    TestLogEvent.FAILED,
                    TestLogEvent.PASSED,
                    TestLogEvent.SKIPPED,
                    TestLogEvent.STANDARD_ERROR,
                    TestLogEvent.STANDARD_OUT,
                )
            exceptionFormat = TestExceptionFormat.FULL
        }
        info.events = debug.events
        info.exceptionFormat = debug.exceptionFormat
    }
    useJUnitPlatform()
}

tasks.withType(KspTaskJvm::class.java).configureEach {
    dependsOn(":core:shadowJar")
    dependsOn(":faker:books:shadowJar")
    dependsOn(":faker:edu:shadowJar")
}
