import org.gradle.api.tasks.testing.TestResult.ResultType
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import utils.Color
import utils.times

/** Plugin for base build setup of faker modules with kotlin */
plugins {
    id("faker-base-conventions")
    java
    kotlin("jvm")
    id("org.jetbrains.dokka")
    id("org.jetbrains.kotlinx.binary-compatibility-validator")
    id("com.diffplug.spotless")
}

val lib = project.libs

dependencies {
    val implementation by configurations
    val testImplementation by configurations
    // common-for-all dependencies go here
    implementation(platform(lib.kotlin.bom))
    implementation(lib.bundles.kotlin)
    testImplementation(lib.bundles.test.kotest)
}

configure<JavaPluginExtension> { toolchain { languageVersion.set(JavaLanguageVersion.of(8)) } }

configure<KotlinJvmProjectExtension> {
    jvmToolchain { languageVersion.set(JavaLanguageVersion.of(8)) }
}

tasks.withType<JavaCompile> { options.encoding = "UTF-8" }

tasks.withType<Test> {
    @Suppress("UNNECESSARY_SAFE_CALL")
    jvmArgs = jvmArgs?.plus("-ea") ?: listOf("-ea")

    useJUnitPlatform()
    maxParallelForks = 1

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

// configure sourceSets as extension since it's not available here as `sourceSets` is an extension
// on `Project`
// https://docs.gradle.org/current/userguide/kotlin_dsl.html#project_extensions_and_conventions
configure<SourceSetContainer> {
    main { resources { this.srcDir("build/generated/src/main/resources") } }
}

tasks.withType<Jar> {
    archiveBaseName.set(fullName)

    manifest {
        attributes(
            mapOf(
                "Implementation-Title" to fullName,
                "Implementation-Version" to project.version,
                /*
                 * We can't add this here because this resolves the configuration,
                 * after which it effectively becomes read-only and we'll get an error
                 * Cannot change dependencies of dependency configuration ':core:implementation' after it has been included in dependency resolution
                 * if we try to add more dependencies in the module's build.gradle file directly
                 */
                // "Class-Path" to project.configurations.compileClasspath.get().joinToString(" ") {
                // it.name }
            )
        )
    }
}

tasks.withType<DokkaTask>().configureEach {
    onlyIf("Not dev") { !isDev.get() }
    onlyIf("Release or snapshot") { isRelease.get() || isSnapshot.get() }
}

apiValidation {}

spotless {
    kotlin { ktfmt().kotlinlangStyle().configure {} }
    kotlinGradle {
        target("*.gradle.kts")
        ktfmt().kotlinlangStyle().configure {}
    }
}
