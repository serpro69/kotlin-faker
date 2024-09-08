/**
 * Plugin for :extension:* modules
 */

plugins {
    id("faker-kotlin-conventions")
    id("faker-pub-conventions")
}

dependencies {
    val implementation by configurations
    implementation(libs.bundles.kotlin)
}
