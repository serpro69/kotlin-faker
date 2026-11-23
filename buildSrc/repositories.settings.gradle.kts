// shared repository definitions for both the main project and buildSrc

@Suppress("UnstableApiUsage") // Central declaration of repositories is an incubating feature
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)

    repositories {
        mavenCentral()
        gradlePluginPortal()

        // PREFER_SETTINGS ignores KGP's temporary project repositories for JS tool downloads.
        // Match the distribution layouts used by KGP 2.3.21, scoped to those tools only.
        ivy {
            name = "NodeDistributions"
            url = uri("https://nodejs.org/dist")
            patternLayout { artifact("v[revision]/[artifact](-v[revision]-[classifier]).[ext]") }
            metadataSources { artifact() }
            content { includeModule("org.nodejs", "node") }
        }
        ivy {
            name = "YarnDistributions"
            url = uri("https://github.com/yarnpkg/yarn/releases/download")
            patternLayout { artifact("v[revision]/[artifact](-v[revision]).[ext]") }
            metadataSources { artifact() }
            content { includeModule("com.yarnpkg", "yarn") }
        }
    }

    pluginManagement {
        repositories {
            mavenCentral()
            gradlePluginPortal()
        }
    }
}
