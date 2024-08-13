// shared repository definitions for both the main project and buildSrc

@Suppress("UnstableApiUsage") // Central declaration of repositories is an incubating feature
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)

    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven {
            // orchid...
            url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots")
        }
    }

    pluginManagement {
        repositories {
            maven {
                // orchid...
                url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots")
            }
            mavenCentral()
            gradlePluginPortal()
        }
    }
}
