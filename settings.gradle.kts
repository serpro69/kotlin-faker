import io.github.serpro69.semverkt.gradle.plugin.SemverPluginExtension
import io.github.serpro69.semverkt.release.configuration.TagPrefix

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

apply(from = "./buildSrc/repositories.settings.gradle.kts")

plugins {
    // NB! remember to set same version in gradle/libs.versions.toml:10
    id("io.github.serpro69.semantic-versioning") version "0.14.0"
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "kotlin-faker"

include(
    ":bom",
    ":core",
    ":cli-bot",
)

val extensions =
    listOf(
        "blns",
        "kotest-property",
    )
extensions.forEach { include(":extension:$it") }

val fakers =
    listOf(
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
        "pictures",
        "sports",
        "tech",
        "travel",
        "tvshows",
    )
fakers.forEach { include(":faker:$it") }

// helpers for integration tests
include(":test")

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
}
