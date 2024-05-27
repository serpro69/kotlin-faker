/**
 * Plugin for :extension:* modules
 */

plugins {
    id("faker-base-conventions")
    id("faker-pub-conventions")
}

dependencies {
    val implementation by configurations
    implementation(libs.bundles.kotlin)
}
