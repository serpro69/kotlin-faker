plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    // needed to be able to apply external plugin
    // https://docs.gradle.org/current/userguide/custom_plugins.html#applying_external_plugins_in_precompiled_script_plugins
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.20")
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:1.9.10")
    implementation("com.github.johnrengelman:shadow:8.1.1")
    implementation("com.adarshr:gradle-test-logger-plugin:4.0.0")
    // used by yaml-to-json buildSrc plugin
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.3")
    // use snakeyaml instead of jackson-dataformat-yaml to properly handle yaml anchors and write them as actual values to json
    implementation("org.yaml:snakeyaml:2.2")
}
