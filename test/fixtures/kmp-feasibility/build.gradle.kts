import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.gradle.testing.jacoco.tasks.JacocoCoverageVerification
import org.gradle.testing.jacoco.tasks.JacocoReport
import org.gradle.testing.jacoco.tasks.JacocoReportBase

plugins {
    kotlin("multiplatform")
    jacoco
}

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

// JVM measurement for the handwritten random mechanism only; production Kover belongs to Task 31.
jacoco { toolVersion = "0.8.16" }
val randomClasses = fileTree(layout.buildDirectory.dir("classes/kotlin/jvm/main")) {
    include("probe/random/**")
}
val randomExecutionData = layout.buildDirectory.file("jacoco/jvmTest.exec")
val requireRandomCoverageData = tasks.register("requireRandomCoverageData") {
    dependsOn(tasks.named("jvmTest"))
    doLast {
        check(randomExecutionData.get().asFile.isFile) { "Missing JVM coverage execution data" }
        check(!randomClasses.isEmpty) { "Missing compiled random mechanism classes" }
    }
}
val randomCoverageReport = tasks.register<JacocoReport>("randomCoverageReport") {
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}
tasks.register<JacocoCoverageVerification>("randomCoverageVerification") {
    dependsOn(randomCoverageReport)
    violationRules {
        rule {
            limit {
                counter = "CLASS"
                value = "COVEREDCOUNT"
                minimum = "2".toBigDecimal()
            }
            for (metric in listOf("LINE", "BRANCH")) {
                limit {
                    counter = metric
                    minimum = "1.0".toBigDecimal()
                }
            }
        }
    }
}
tasks.withType<JacocoReportBase>().configureEach {
    dependsOn(requireRandomCoverageData)
    executionData(randomExecutionData)
    classDirectories.setFrom(randomClasses)
    sourceDirectories.setFrom(files("src/commonMain/kotlin"))
}

// This fixture probes mechanisms only. No Faker code is copied here.
apply(from = "inventory.gradle.kts")
