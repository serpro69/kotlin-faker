import io.github.serpro69.semverkt.gradle.plugin.SemverPluginExtension
import io.github.serpro69.semverkt.release.configuration.TagPrefix

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

apply(from = "./buildSrc/repositories.settings.gradle.kts")

plugins {
    // NB! remember to set same version in gradle/libs.versions.toml:10
    id("io.github.serpro69.semantic-versioning") version "0.13.0"
}

rootProject.name = "kotlin-faker"

include(
    "bom",
    "core",
    "cli-bot",
    "docs",
    "kotest-property",
    "kotest-property-test",
)

include("")

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
    version {
        useSnapshots = true
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
