import Yaml_to_json_gradle.Yaml2JsonPlugin
import Yaml_to_json_gradle.Yaml2JsonPluginExtension
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    `faker-lib-conventions`
    `yaml-to-json`
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.3")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.3")
    shadow("com.ibm.icu:icu4j:73.2")
    shadow("com.github.mifmif:generex:1.0.2")
    // integration dependencies for classes in docs package, e.g. Homepage.kt
    // NB! only add fakers that are needed
    val integrationImplementation by configurations
    integrationImplementation(project(":faker:commerce"))
    integrationImplementation(project(":faker:movies"))
    integrationImplementation(project(":faker:tvshows"))
}

// integrationTest task must run after we've built the dependencies
tasks.getByName("integrationTest") {
    dependsOn(":faker:commerce:shadowJar")
    dependsOn(":faker:movies:shadowJar")
    dependsOn(":faker:tvshows:shadowJar")
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
