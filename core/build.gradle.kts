import Yaml_to_json_gradle.Yaml2JsonPlugin
import Yaml_to_json_gradle.Yaml2JsonPluginExtension
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    `faker-lib-conventions`
    `yaml-to-json`
}

dependencies {
    implementation(libs.bundles.jackson)
    shadow(libs.icu4j)
    shadow(libs.generex)
}

apply<Yaml2JsonPlugin>() // this shouldn't really be needed since the plugin is supposed to be applied in the plugins{} block
configure<Yaml2JsonPluginExtension> {
    val cwd = project.projectDir.absolutePath
    input.set(File("$cwd/src/main/resources/locales"))
    output.set(File("$cwd/build/generated/src/main/resources"))
}

tasks.processResources.get().dependsOn(tasks["yaml2json"])

tasks.withType<Jar>().configureEach {
    manifest {
        // set the classpath attribute here because we can't modify it in a buildSrc plugin or someplace else
        // see comment in the ShadowJar task config of the 'faker-lib-conventions' buildSrc plugin
        attributes["Class-Path"] = project.configurations.compileClasspath.get().joinToString(" ") { it.name }
    }
}

tasks.withType<ShadowJar>().configureEach {
    manifest {
        // set the classpath attribute here because we can't modify it in a buildSrc plugin or someplace else
        // see comment in the ShadowJar task config of the 'faker-lib-conventions' buildSrc plugin
        attributes["Class-Path"] = project.configurations.compileClasspath.get().joinToString(" ") { it.name }
    }
}
