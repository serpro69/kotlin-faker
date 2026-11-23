pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
    plugins {
        kotlin("multiplatform") version providers.gradleProperty("probeKotlinVersion").getOrElse("2.3.21")
    }
}

dependencyResolutionManagement { repositories { mavenCentral() } }

// Deliberately not included in the production settings, BOM or release aggregation.
rootProject.name = "kfaker-kmp-feasibility"
