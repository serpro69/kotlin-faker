import Yaml_to_json_gradle.Yaml2JsonPlugin
import Yaml_to_json_gradle.Yaml2JsonPluginExtension

plugins {
    `faker-lib-conventions`
    `yaml-to-json`
}

kotlin {
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(libs.bundles.jackson)
                implementation(libs.icu4j)
                implementation(libs.rgxgen)
            }
        }
    }
}

// this shouldn't really be needed
// since the plugin is supposed to be applied in the plugins{}
apply<Yaml2JsonPlugin>()

configure<Yaml2JsonPluginExtension> {
    val cwd = project.projectDir.absolutePath
    input.set(File("$cwd/src/main/resources/locales"))
    output.set(File("$cwd/build/generated/src/main/resources"))
}

tasks.withType<ProcessResources>().configureEach {
    dependsOn(tasks["yaml2json"])
}
