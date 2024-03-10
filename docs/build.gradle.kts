@file:Suppress("KDocMissingDocumentation")

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/*
This module builds the kotlin-faker docs with Orchid.

Commands:
    gradle :site:orchidServe
        build the site and serve it locally on http://localhost:8080. Changes to
        site content will rebuild the site.
    gradle :site:orchidDeploy -Penv=prod
        build the site and deploy it to Github Pages. Requires an API token with
        push access to the repo, set as `github_token` in Gradle properties, or
        a `GITHUB_TOKEN` environment variable. The `env` project property will
        set the appropriate site base URL.
*/

plugins {
    kotlin("jvm")
    id("com.eden.orchidPlugin") version "0.21.1"
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    orchidImplementation("io.github.javaeden.orchid:OrchidCore:0.21.1")
    orchidImplementation("io.github.javaeden.orchid:OrchidCopper:0.21.1")
    orchidRuntimeOnly("io.github.javaeden.orchid:OrchidDocs:0.21.1")
    orchidRuntimeOnly("io.github.javaeden.orchid:OrchidPluginDocs:0.21.1")
    orchidRuntimeOnly("io.github.javaeden.orchid:OrchidKotlindoc:0.21.1")
    orchidRuntimeOnly("io.github.javaeden.orchid:OrchidGithub:0.21.1")
    orchidRuntimeOnly("io.github.javaeden.orchid:OrchidSnippets:0.21.1")
    orchidRuntimeOnly("io.github.javaeden.orchid:OrchidAsciidoc:0.21.1")
}

project.version = "${project.version}"

orchid {
    environment = if (findProperty("env") == "prod") "prod" else "debug"
    args = listOf("--experimentalSourceDoc")

    githubToken = if (hasProperty("github_token")) {
        property("github_token").toString()
    } else {
        System.getenv("GITHUB_TOKEN")
    }
}
