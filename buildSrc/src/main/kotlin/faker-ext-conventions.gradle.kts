/**
 * Plugin for :extension:* modules
 */

plugins {
    id("faker-jvm-conventions")
    id("faker-pub-conventions")
}

kotlin {
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(libs.bundles.kotlin)
            }
        }
    }
}
