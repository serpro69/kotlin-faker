import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import io.qameta.allure.gradle.task.AllureReport
import io.qameta.allure.gradle.task.AllureServe
import org.gradle.api.tasks.testing.TestResult.ResultType
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

plugins {
    // NB! some versions are on the classpath from dependency declared in buildSrc/build.gradle.kts
    kotlin("jvm") apply false
    id("com.adarshr.test-logger") apply false
    id("com.github.ben-manes.versions") version "0.28.0" apply false
    id("io.qameta.allure") version "2.8.1"
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
    id("com.github.johnrengelman.shadow") apply false
}

repositories {
    mavenCentral()
}

group = "io.github.serpro69"

subprojects {
    group = rootProject.group.toString()
    version = rootProject.version.toString()

    val isTestHelper = this@subprojects.name == "test"

    repositories {
        mavenCentral()
    }

    apply {
        plugin("java")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("com.adarshr.test-logger")
        plugin("com.github.ben-manes.versions")
        plugin("io.qameta.allure")
        if (!isTestHelper) plugin("com.github.johnrengelman.shadow")
    }

    dependencies {
        val implementation by configurations
        val testRuntimeOnly by configurations
        val testImplementation by configurations

        // TODO move dependencies to conventions plugin(s)
        implementation(kotlin("stdlib-jdk8"))
        implementation(kotlin("reflect"))
        implementation("com.github.mifmif:generex:1.0.2")
        testImplementation("io.kotest:kotest-runner-junit5:5.7.2")
        testImplementation("io.kotest:kotest-runner-junit5-jvm:5.7.2")
        testImplementation("io.kotest:kotest-assertions-core-jvm:5.7.2")
        testImplementation("io.kotest:kotest-property-jvm:5.7.2")
        testImplementation("io.kotest.extensions:kotest-extensions-allure:1.3.0")
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0")
        testRuntimeOnly("ch.qos.logback:logback-core:1.3.4") {
            version {
                strictly("1.3.4") // last stable for java 8
            }
        }
        testRuntimeOnly("ch.qos.logback:logback-classic:1.3.4") {
            version {
                strictly("1.3.4") // last stable for java 8
            }
        }
        testRuntimeOnly("org.codehaus.groovy:groovy:3.0.19")
    }

    configure<JavaPluginExtension> {
        toolchain {
            languageVersion = JavaLanguageVersion.of(8)
            vendor.set(JvmVendorSpec.matching("Temurin"))
        }
    }

    configure<KotlinJvmProjectExtension> {
        jvmToolchain {
            languageVersion.set(JavaLanguageVersion.of(8))
            vendor.set(JvmVendorSpec.matching("Temurin"))
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

    allure {
        version = "2.8.1"
        aspectjweaver = false
        aspectjVersion = "1.9.20.1"
        autoconfigure = true
        // TODO check if fixed in future versions of allure
        configuration = "testRuntimeOnly" // defaults to 'testCompile' which is incompatible with gradle 7.x
        allureJavaVersion = "2.24.0"
        useJUnit5 {
            version = "2.24.0"
        }
    }
}

rootProject.allure {
    version = "2.8.1"
}

val allureAggregatedReport by tasks.creating(AllureReport::class) {
    doFirst {
        val results = mutableListOf<File>()

        subprojects.stream().forEach {
            it.allure.resultsDir?.let { dir ->
                results.add(dir)
            }
        }

        resultsDirs = results
    }
}

val allureAggregatedServe by tasks.creating(AllureServe::class) {
    doFirst {
        val results = mutableListOf<File>()

        subprojects.stream().forEach {
            it.allure.resultsDir?.let { dir ->
                results.add(dir)
            }
        }

        resultsDirs = results
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
