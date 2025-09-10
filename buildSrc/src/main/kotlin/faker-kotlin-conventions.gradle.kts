import org.gradle.api.tasks.testing.TestResult.ResultType
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.dokka.gradle.DokkaTask
import utils.Color
import utils.times

/** Plugin for build setup of faker modules with kotlin */
plugins {
    id("faker-base")
    kotlin("multiplatform")
    id("org.jetbrains.dokka")
    id("com.diffplug.spotless")
}

kotlin {
    sourceSets.configureEach {
        withSourcesJar()

        resources.srcDir("build/generated/src/jvmMain/resources")
        dependencies {
            implementation(platform(libs.kotlin.bom.get()))
            implementation(libs.bundles.kotlin)
        }
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()

    testLogging {
        // set options for log level LIFECYCLE
        events = setOf(TestLogEvent.FAILED, TestLogEvent.SKIPPED, TestLogEvent.STANDARD_OUT)
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

        afterSuite(
            KotlinClosure2({ desc: TestDescriptor, result: TestResult ->
                if (desc.parent == null) { // will match the outermost suite
                    val pass = "${Color.GREEN}${result.successfulTestCount} passed${Color.NONE}"
                    val fail = "${Color.RED}${result.failedTestCount} failed${Color.NONE}"
                    val skip = "${Color.YELLOW}${result.skippedTestCount} skipped${Color.NONE}"
                    val type =
                        when (val r: ResultType = result.resultType) {
                            ResultType.SUCCESS -> "${Color.GREEN}$r${Color.NONE}"
                            ResultType.FAILURE -> "${Color.RED}$r${Color.NONE}"
                            ResultType.SKIPPED -> "${Color.YELLOW}$r${Color.NONE}"
                        }
                    val output = "Results: $type (${result.testCount} tests, $pass, $fail, $skip)"
                    val startItem = "|   "
                    val endItem = "   |"
                    val repeatLength = startItem.length + output.length + endItem.length - 36
                    println("")
                    println(
                        "\n" +
                            ("-" * repeatLength) +
                            "\n" +
                            startItem +
                            output +
                            endItem +
                            "\n" +
                            ("-" * repeatLength)
                    )
                }
            })
        )
    }

    onOutput(
        KotlinClosure2({ _: TestDescriptor, event: TestOutputEvent ->
            if (event.destination == TestOutputEvent.Destination.StdOut) {
                logger.lifecycle(event.message.replace(Regex("""\s+$"""), ""))
            }
        })
    )
}

tasks.withType<DokkaTask>().configureEach {
    onlyIf("Not dev") { !isDev.get() }
    onlyIf("Release or snapshot") { isRelease.get() || isSnapshot.get() }
}

spotless {
    kotlin { ktfmt().kotlinlangStyle().configure {} }
    kotlinGradle {
        target("*.gradle.kts")
        ktfmt().kotlinlangStyle().configure {}
    }
}
