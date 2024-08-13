@file:Suppress("KDocMissingDocumentation")

import io.github.serpro69.semverkt.gradle.plugin.tasks.TagTask
import kotlinx.validation.KotlinApiBuildTask
import kotlinx.validation.KotlinApiCompareTask
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
    maven {
        url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots")
    }
    mavenCentral()
    jcenter() // orchid...
}

dependencies {
    // Looks like a snapshot version was published to central before active development stopped
    // https://s01.oss.sonatype.org/content/repositories/snapshots/io/github/copper-leaf/orchid/
    orchidImplementation("io.github.copper-leaf.orchid:orchid-core:1.0.0-SNAPSHOT")
    orchidImplementation("io.github.copper-leaf.orchid:orchid-copper-theme:1.0.0-SNAPSHOT")
    orchidRuntimeOnly("io.github.copper-leaf.orchid:orchid-docs-bundle:1.0.0-SNAPSHOT")
    orchidRuntimeOnly("io.github.copper-leaf.orchid:orchid-plugin-docs-feature:1.0.0-SNAPSHOT")
    orchidRuntimeOnly("io.github.copper-leaf.orchid:orchid-kotlindoc-feature:1.0.0-SNAPSHOT")
    orchidRuntimeOnly("io.github.copper-leaf.orchid:orchid-github-feature:1.0.0-SNAPSHOT")
    orchidRuntimeOnly("io.github.copper-leaf.orchid:orchid-snippets-feature:1.0.0-SNAPSHOT")
    orchidRuntimeOnly("io.github.copper-leaf.orchid:orchid-asciidoc-feature:1.0.0-SNAPSHOT")
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

tasks.withType<TagTask> {
    enabled = false
}
