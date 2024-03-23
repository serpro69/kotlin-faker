import io.github.serpro69.semverkt.gradle.plugin.SemverPluginExtension
import io.github.serpro69.semverkt.release.configuration.TagPrefix

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("io.github.serpro69.semantic-versioning") version "0.10.0"
}

rootProject.name = "kotlin-faker"

include(
    "core",
    "cli-bot",
    "docs"
)

val fakers = listOf(
    "books",
    "commerce",
    "creatures",
    "databases",
    "edu",
    "games",
    "humor",
    "japmedia",
    "lorem",
    "misc",
    "movies",
    "music",
    "sports",
    "tech",
    "travel",
    "tvshows",
)

fakers.forEach { include("faker:$it") }

// helpers for integration tests
include("test")

settings.extensions.configure<SemverPluginExtension>("semantic-versioning") {
    git {
        message {
            preRelease = "[rc]"
            ignoreCase = true
        }
    }
    monorepo {
        fakers.forEach { f ->
            module(":faker:$f") {
                tag {
                    prefix = TagPrefix("faker-$f-v")
                }
            }
        }
    }
}
