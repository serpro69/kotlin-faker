import utils.VerifyCommonCoverage

/** Opt-in targets for portable libraries; domain modules adopt this after core is complete. */
plugins {
    id("faker-lib-conventions")
    id("org.jetbrains.kotlinx.kover")
}

val commonCoverageReport = layout.buildDirectory.file("reports/kover/report.xml")

kover.reports.total.xml { xmlFile.set(commonCoverageReport) }

val verifyCommonCoverage by
    tasks.registering(VerifyCommonCoverage::class) {
        group = "verification"
        description = "Require 100% measured JVM line/branch coverage of handwritten common sources"
        report.set(commonCoverageReport)
        commonSources.from(fileTree("src/commonMain/kotlin") { include("**/*.kt") })
        dependsOn(tasks.named("koverXmlReport"))
    }

tasks.named("koverVerify") { dependsOn(verifyCommonCoverage) }

tasks.named("check") { dependsOn(verifyCommonCoverage) }

val portableSettings = extensions.getByType<BuildSettings>()

kotlin {
    if (portableSettings.enableKotlinJs.get()) {
        js {
            browser { testTask { useKarma { useChromeHeadless() } } }
            nodejs()
        }
    }
    if (portableSettings.enableKotlinNative.get()) {
        iosArm64()
        iosSimulatorArm64()
    }

    sourceSets {
        commonTest.dependencies { implementation(kotlin("test")) }
        jvmTest.dependencies {
            implementation(kotlin("test-junit5"))
            runtimeOnly(libs.test.junit.jupiter.engine)
            runtimeOnly(libs.test.junit.platform.launcher)
        }
    }
}
