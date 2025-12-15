import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import io.github.serpro69.semverkt.gradle.plugin.tasks.TagTask
import org.gradle.api.tasks.testing.TestResult.ResultType
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.gradle.internal.os.OperatingSystem
import utils.times
import utils.Color

plugins {
    application
    kotlin("jvm")
    id("org.graalvm.buildtools.native") version "0.10.6"
    id("com.gradleup.shadow") version "9.0.2"
}

val mainFunction = "io.github.serpro69.kfaker.app.KFakerKt"
val mainAppClass = "io.github.serpro69.kfaker.app.KFaker"

val fakers =
    listOf(
        "books",
        "commerce",
        "creatures",
        "databases",
        "edu",
        "games",
        "humor",
        "japmedia",
        "lorem",
        "misc",
        "movies",
        "music",
        "sports",
        "tech",
        "travel",
        "tvshows",
    )

dependencies {
    implementation(libs.bundles.kotlin)
    implementation(projects.kotlinFaker)
    fakers.forEach { implementation(project(path = ":faker:kotlin-faker-$it")) }
    implementation("info.picocli:picocli:4.7.7")
    testImplementation(libs.bundles.test.kotest)
}

// Test tasks must run after we've built the dependencies
tasks.withType<Test> {
    dependsOn(":kotlin-faker:jvmJar")
    fakers.forEach { dependsOn(":faker:kotlin-faker-$it:jvmJar") }
}

application {
    //    mainClassName = mainFunction
    mainClass.set(mainFunction)
}

java { toolchain { languageVersion.set(JavaLanguageVersion.of(11)) } }

val shadowJar by
    tasks.getting(ShadowJar::class) {
        manifest {
            attributes(
                mapOf(
                    "Implementation-Title" to project.name,
                    "Implementation-Version" to project.version,
                    "Class-Path" to
                        project.configurations.compileClasspath.get().joinToString(" ") { it.name },
                    "Main-Class" to mainFunction,
                )
            )
        }

        archiveClassifier.set("fat")
        from(sourceSets.main.get().output)
        //    from(project.configurations.runtimeClasspath.get().map { if (it.isDirectory) it else
        // zipTree(it) })
        from(
            project.configurations.runtimeClasspath
                .get()
                .filter { it.name.endsWith("jar") }
                .map { zipTree(it) }
        )
        with(tasks.jar.get() as CopySpec)
        dependsOn(project.configurations.runtimeClasspath)
        // since we're adding :core and :faker:* as implementation dependencies
        // we also need to make sure ShadowJar task depend on core having been built
        dependsOn(":kotlin-faker:jvmJar")
        fakers.forEach { dependsOn(":faker:kotlin-faker-$it:jvmJar") }
    }

// dunno why, but gradle is complaining that 'startScripts' may run before dependencies have been
// built
tasks.startScripts {
    dependsOn(":kotlin-faker:jvmJar")
    fakers.forEach { dependsOn(":faker:kotlin-faker-$it:jvmJar") }
}

graalvmNative {
    testSupport = false
    toolchainDetection = true
    binaries {
        named("main") {
            imageName = "faker-bot_${project.version}"
            javaLauncher =
                javaToolchains.launcherFor {
                    languageVersion.set(JavaLanguageVersion.of(17))
                    vendor.set(JvmVendorSpec.GRAAL_VM)
                }
            mainClass.set(mainFunction)
        }
    }
}

// graalvmNative {
//    graalVersion("21.2.0")
//    javaVersion("8")
//    mainClass(mainFunction)
//    outputName("faker-bot_${project.version}")
//    option("--no-fallback")
//    option("--no-server")
//    option("--report-unsupported-elements-at-runtime")
// }

tasks {
    all {
        onlyIf("enable cli") {
            project.hasProperty("kotlinFaker_enableCli")
        }
    }

    compileKotlin {
        // Set version for --version options
        doFirst("Set app version") {
            val sed = if (OperatingSystem.current().isMacOsX) "gsed" else "sed"
            val command =
                "find . -type f -name 'KFaker.kt' -exec $sed -i 's/{FAKER_VER}/${project.version}/g' {} +;"

            exec { commandLine("sh", "-c", command) }
        }

        // Restore the file so it's not accidentally committed
        doLast { exec { commandLine("sh", "-c", "git checkout *KFaker.kt") } }
        dependsOn(":kotlin-faker:assemble")
    }

    nativeCompile { dependsOn(shadowJar) }

    generateResourcesConfigFile { fakers.forEach { dependsOn(":faker:kotlin-faker-$it:jvmJar") } }
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

// depend on root tag task to avoid failures due to race conditions in parallel tasks executations
tasks.withType<TagTask>().configureEach { dependsOn(":tag") }
