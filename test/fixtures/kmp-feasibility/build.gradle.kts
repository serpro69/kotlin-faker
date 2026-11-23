import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins { kotlin("multiplatform") }

group = "io.github.serpro69.kfaker.probe"
version = "0.0.0-local"

apply(from = "payload.gradle.kts")

kotlin {
    jvmToolchain(21)
    jvm {
        compilerOptions { jvmTarget.set(JvmTarget.JVM_11) }
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
            systemProperty("payload.sourceRoot", file("../../../core/src/jvmMain/resources/locales").absolutePath)
            inputs.dir(file("../../../core/src/jvmMain/resources/locales"))
            javaLauncher.set(javaToolchains.launcherFor {
                languageVersion.set(JavaLanguageVersion.of(
                    providers.gradleProperty("probeTestJdk").getOrElse("21")
                ))
            })
        }
    }
    js {
        browser { testTask { useKarma { useChromeHeadless() } } }
        nodejs()
    }
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain {
            kotlin.srcDir(files(layout.buildDirectory.dir("generated/payload/commonMain"))
                .builtBy(tasks.named("generatePayload")))
            dependencies { implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.11.0") }
        }
        commonTest {
            kotlin.srcDir(files(layout.buildDirectory.dir("generated/payload/commonTest"))
                .builtBy(tasks.named("generatePayload")))
            dependencies {
                implementation(kotlin("test"))
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.8.0")
            }
        }
        jvmTest.dependencies {
            implementation(kotlin("test-junit5"))
            implementation("org.yaml:snakeyaml:2.2")
            implementation("com.fasterxml.jackson.core:jackson-databind:2.21.1")
            runtimeOnly("org.junit.jupiter:junit-jupiter-engine:5.11.0")
            runtimeOnly("org.junit.platform:junit-platform-launcher:1.11.0")
        }
    }
}

// This fixture probes dependencies and the complete embedded payload. No Faker code is copied here.
apply(from = "inventory.gradle.kts")
