pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
    plugins { kotlin("multiplatform") version "2.3.20" }
}

dependencyResolutionManagement {
    repositories {
        maven {
            name = "Probe"
            url = uri("../build/probe-repository")
            content { includeGroup("io.github.serpro69.kfaker.probe") }
        }
        mavenCentral { content { excludeGroup("io.github.serpro69.kfaker.probe") } }
    }
}

// Independent build: no includes, source sharing, Maven Local or project substitution.
rootProject.name = "kfaker-probe-consumer"
