import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.gradle.testing.jacoco.tasks.JacocoCoverageVerification
import org.gradle.testing.jacoco.tasks.JacocoReport
import org.gradle.testing.jacoco.tasks.JacocoReportBase

plugins {
    kotlin("multiplatform")
    jacoco
}

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
        commonMain.dependencies {
            implementation("io.github.serpro69.kfaker.probe:kfaker-kmp-feasibility:0.0.0-local")
        }
        commonTest.dependencies { implementation(kotlin("test")) }
        jvmTest.dependencies {
            implementation(kotlin("test-junit5"))
            runtimeOnly("org.junit.jupiter:junit-jupiter-engine:5.11.0")
            runtimeOnly("org.junit.platform:junit-platform-launcher:1.11.0")
        }
    }
}

// Measure the new boundary in the resolved artifact, never the producer's class directory.
jacoco { toolVersion = "0.8.16" }
val boundaryClasses = files(provider {
    val artifact = configurations.getByName("jvmTestRuntimeClasspath").single {
        it.name == "kfaker-kmp-feasibility-jvm-0.0.0-local.jar"
    }
    zipTree(artifact).matching { include("probe/MechanismProbe.class") }
}, fileTree(layout.buildDirectory.dir("classes/kotlin/jvm/main")) {
    include("consumer/ProbeConsumerKt.class")
})
val boundaryExecutionData = layout.buildDirectory.file("jacoco/jvmTest.exec")
val requireBoundaryCoverageData = tasks.register("requireBoundaryCoverageData") {
    dependsOn("jvmTest")
    doLast {
        check(boundaryExecutionData.get().asFile.isFile) { "Missing consumer JVM coverage data" }
        check(boundaryClasses.files.size == 2) { "Missing published boundary or common consumer classes" }
    }
}
val boundaryCoverageReport = tasks.register<JacocoReport>("boundaryCoverageReport") {
    reports { xml.required.set(true) }
}
tasks.register<JacocoCoverageVerification>("boundaryCoverageVerification") {
    dependsOn(boundaryCoverageReport)
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
    dependsOn(requireBoundaryCoverageData)
    executionData(boundaryExecutionData)
    classDirectories.setFrom(boundaryClasses)
}
