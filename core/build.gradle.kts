import Yaml_to_json_gradle.Yaml2JsonPlugin
import Yaml_to_json_gradle.Yaml2JsonPluginExtension

plugins {
    `faker-lib-conventions`
    `yaml-to-json`
}

dependencies {
    implementation(libs.bundles.jackson)
    implementation(libs.icu4j)
    implementation(libs.rgxgen)
}

apply<
    Yaml2JsonPlugin
>() // this shouldn't really be needed since the plugin is supposed to be applied in the plugins{}

// block

configure<Yaml2JsonPluginExtension> {
    val cwd = project.projectDir.absolutePath
    input.set(File("$cwd/src/main/resources/locales"))
    output.set(File("$cwd/build/generated/src/main/resources"))
}

tasks.processResources.get().dependsOn(tasks["yaml2json"])
