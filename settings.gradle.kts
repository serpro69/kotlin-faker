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

val fakers = listOf(
    "books",
    "commerce",
    "creatures",
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
