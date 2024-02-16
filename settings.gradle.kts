plugins {
    id("com.gradle.enterprise") version "3.1"
}

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
    }
}

rootProject.name = "kotlin-faker"

include(
    "core",
    "cli-bot",
    "docs"
)

val providers = listOf(
    "books",
    "games",
    "movies",
    "music",
    "sports",
    "tvshows",
)

providers.forEach { include("provider:$it") }
