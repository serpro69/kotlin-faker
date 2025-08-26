plugins {
    `kotlin-dsl`
//    kotlin("jvm") version embeddedKotlinVersion
}

dependencies {
    //https://github.com/gradle/gradle/issues/15383#issuecomment-779893192
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
    implementation(platform(libs.kotlin.bom))
    implementation(libs.gradle.plugin.devpublish) // TODO: move to plugins bundle
    // needed to be able to apply external plugin
    // https://docs.gradle.org/current/userguide/custom_plugins.html#applying_external_plugins_in_precompiled_script_plugins
    implementation(libs.bundles.gradle.plugins)
    // used by yaml-to-json buildSrc plugin
    implementation(libs.jackson.databind)
    // use snakeyaml instead of jackson-dataformat-yaml to properly handle yaml anchors and write them as actual values to json
    implementation(libs.snakeyaml)
}

tasks.withType<AbstractArchiveTask>().configureEach {
    // https://docs.gradle.org/current/userguide/working_with_files.html#sec:reproducible_archives
    isPreserveFileTimestamps = false
    isReproducibleFileOrder = true
}
