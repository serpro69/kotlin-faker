import kotlin.jvm.optionals.getOrElse
import org.jetbrains.kotlin.gradle.plugin.KotlinTargetWithTests.Companion.DEFAULT_TEST_RUN_NAME
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile
import utils.SystemPropertiesArgumentProvider
import utils.asInt
import utils.jdkRelease
import utils.jvmTarget

/** Plugin for kotlin-jvm targets */
plugins { id("faker-kotlin-conventions") }

kotlin {
    jvm {
        withSourcesJar()

        // Use a 'release' version Java launcher for the main tests,
        // to ensure that kotlin-faker can actually be used with the 'release' version.
        testRuns.named(DEFAULT_TEST_RUN_NAME) {
            executionTask.configure {
                javaLauncher = javaToolchains.launcherFor { languageVersion = jvmMinTargetVersion }
            }
        }
        // Use the 'max' version, to ensure that kotlin-faker works with the latest Java version.
        val maxJdk by
            testRuns.creating {
                executionTask.configure {
                    javaLauncher =
                        javaToolchains.launcherFor { languageVersion = jvmMaxTargetVersion }
                }
            }
        tasks.check { dependsOn(maxJdk.executionTask) }
    }

    sourceSets { jvmTest { dependencies { implementation(libs.bundles.test.kotest) } } }
}

val versionCatalog: VersionCatalog = versionCatalogs.named("libs")

fun VersionCatalog.findJvmVersion(name: String): Provider<JavaLanguageVersion> = provider {
    val version =
        versionCatalog.findVersion(name).getOrElse {
            error("Missing '$name' version in libs.versions.toml")
        }
    JavaLanguageVersion.of(version.requiredVersion)
}

/** The minimum Java version that kotlin-faker supports. */
val jvmMinTargetVersion: Provider<JavaLanguageVersion> =
    versionCatalog.findJvmVersion("jvmMinTarget")

/** The maximum Java version that kotlin-faker supports. */
val jvmMaxTargetVersion: Provider<JavaLanguageVersion> =
    versionCatalog.findJvmVersion("jvmMaxTarget")

/** The Java version used for compilation. */
val jvmCompilerVersion: Provider<JavaLanguageVersion> = versionCatalog.findJvmVersion("jvmCompiler")

// region configure Java compiler
kotlin { jvmToolchain { languageVersion = jvmCompilerVersion } }

// endregion

// region Configure Java target version
tasks.withType<KotlinJvmCompile>().configureEach {
    compilerOptions {
        jdkRelease(jvmMinTargetVersion)
        jvmTarget = jvmMinTargetVersion.jvmTarget()
    }
}

tasks.withType<JavaCompile>().configureEach { options.release = jvmMinTargetVersion.asInt() }

// endregion

// region JVM Test config
tasks.withType<Test>().configureEach {
    jvmArgs = jvmArgs.plus("-ea")

    jvmArgumentProviders.add(
        SystemPropertiesArgumentProvider.SystemPropertiesArgumentProvider(
            javaLauncher.map {
                "testJavaLauncherVersion" to it.metadata.languageVersion.asInt().toString()
            }
        )
    )
}

// endregion
