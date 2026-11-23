import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins { kotlin("multiplatform") }

group = "io.github.serpro69.kfaker.probe"
version = "0.0.0-local"

kotlin {
    jvmToolchain(21)
    jvm {
        compilerOptions { jvmTarget.set(JvmTarget.JVM_11) }
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
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
        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.11.0")
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.8.0")
        }
        jvmTest.dependencies {
            implementation(kotlin("test-junit5"))
            runtimeOnly("org.junit.jupiter:junit-jupiter-engine:5.11.0")
            runtimeOnly("org.junit.platform:junit-platform-launcher:1.11.0")
        }
    }
}

// This fixture only probes dependencies and test discovery. No Faker code is copied here.
apply(from = "inventory.gradle.kts")
