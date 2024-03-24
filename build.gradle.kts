import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import io.github.serpro69.semverkt.gradle.plugin.tasks.TagTask
import org.gradle.api.tasks.testing.TestResult.ResultType
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

plugins {
    // NB! some versions are on the classpath from dependency declared in buildSrc/build.gradle.kts
    kotlin("jvm") apply false
    id("io.github.gradle-nexus.publish-plugin") version "1.3.0"
    id("io.github.serpro69.semantic-versioning") apply false
    id("com.github.ben-manes.versions") version "0.51.0" apply false
}

repositories {
    mavenCentral()
}

group = "io.github.serpro69"

subprojects {
    group = rootProject.group.toString()

    repositories {
        mavenCentral()
    }

    apply {
        plugin("com.github.ben-manes.versions")
    }

    // don't apply the rest to bom subproject
    if (this@subprojects.name == "bom") return@subprojects

    apply {
        plugin("java")
        plugin("org.jetbrains.kotlin.jvm")
    }

    dependencies {
        val implementation by configurations
        val testImplementation by configurations
        val testRuntimeOnly by configurations
        // common-for-all dependencies go here
        platform(kotlin("bom"))
        implementation(kotlin("stdlib-jdk8"))
        implementation(kotlin("reflect"))
        testImplementation("io.kotest:kotest-runner-junit5:5.7.2")
        testImplementation("io.kotest:kotest-assertions-core:5.7.2")
        testImplementation("io.kotest:kotest-property-jvm:5.7.2")
    }

    configure<JavaPluginExtension> {
        toolchain {
            languageVersion = JavaLanguageVersion.of(8)
        }
    }

    configure<KotlinJvmProjectExtension> {
        jvmToolchain {
            languageVersion.set(JavaLanguageVersion.of(8))
        }
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    tasks.withType<Test> {
        @Suppress("SimpleRedundantLet", "UNNECESSARY_SAFE_CALL")
        jvmArgs?.let { it.plus("-ea") }

        useJUnitPlatform()
        maxParallelForks = 1

        testLogging {
            // set options for log level LIFECYCLE
            events = setOf(
                TestLogEvent.FAILED,
                TestLogEvent.SKIPPED,
                TestLogEvent.STANDARD_OUT
            )
            exceptionFormat = TestExceptionFormat.FULL
            showExceptions = true
            showCauses = true
            showStackTraces = true
            // set options for log level DEBUG and INFO
            debug {
                events = setOf(
                    TestLogEvent.STARTED,
                    TestLogEvent.FAILED,
                    TestLogEvent.PASSED,
                    TestLogEvent.SKIPPED,
                    TestLogEvent.STANDARD_ERROR,
                    TestLogEvent.STANDARD_OUT
                )
                exceptionFormat = TestExceptionFormat.FULL
            }
            info.events = debug.events
            info.exceptionFormat = debug.exceptionFormat

            afterSuite(KotlinClosure2({ desc: TestDescriptor, result: TestResult ->
                if (desc.parent == null) { // will match the outermost suite
                    val pass = "${Color.GREEN}${result.successfulTestCount} passed${Color.NONE}"
                    val fail = "${Color.RED}${result.failedTestCount} failed${Color.NONE}"
                    val skip = "${Color.YELLOW}${result.skippedTestCount} skipped${Color.NONE}"
                    val type = when (val r: ResultType = result.resultType) {
                        ResultType.SUCCESS -> "${Color.GREEN}$r${Color.NONE}"
                        ResultType.FAILURE -> "${Color.RED}$r${Color.NONE}"
                        ResultType.SKIPPED -> "${Color.YELLOW}$r${Color.NONE}"
                    }
                    val output = "Results: $type (${result.testCount} tests, $pass, $fail, $skip)"
                    val startItem = "|   "
                    val endItem = "   |"
                    val repeatLength = startItem.length + output.length + endItem.length - 36
                    println("")
                    println("\n" + ("-" * repeatLength) + "\n" + startItem + output + endItem + "\n" + ("-" * repeatLength))
                }
            }))
        }

        onOutput(KotlinClosure2({ _: TestDescriptor, event: TestOutputEvent ->
            if (event.destination == TestOutputEvent.Destination.StdOut) {
                logger.lifecycle(event.message.replace(Regex("""\s+$"""), ""))
            }
        }))
    }

    tasks.withType<DependencyUpdatesTask> {
        fun isNonStable(version: String): Boolean {
            val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
            val regex = "^[0-9,.v-]+(-r|-jre)?$".toRegex()
            val isStable = stableKeyword || regex.matches(version)
            return isStable.not()
        }

        rejectVersionIf {
            isNonStable(candidate.version)
        }
    }
}

nexusPublishing {
    repositories {
        sonatype {
            stagingProfileId.set(properties["stagingProfileId"]?.toString())
        }
    }
}

operator fun String.times(x: Int): String {
    return List(x) { this }.joinToString("")
}

internal enum class Color(ansiCode: Int) {
    NONE(0),
    BLACK(30),
    RED(31),
    GREEN(32),
    YELLOW(33),
    BLUE(34),
    PURPLE(35),
    CYAN(36),
    WHITE(37);

    private val ansiString: String = "\u001B[${ansiCode}m"

    override fun toString(): String {
        return ansiString
    }
}

// Run :tag only after we've published artifacts to sonatype
tasks.withType<TagTask>().configureEach {
    // don't apply when "dryRun"
    findProperty("dryRun") ?: run {
        dependsOn("closeSonatypeStagingRepository")
    }
}
