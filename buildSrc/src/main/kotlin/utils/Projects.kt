import BuildSettings.Companion.releaseCandidateVersionRegex
import BuildSettings.Companion.releaseVersionRegex
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.the

// region manually define accessors, because IntelliJ _still_ doesn't index them properly :(
val Project.libs: LibrariesForLibs
    get() = the<LibrariesForLibs>()

// endregion

// region project modules
val Project.isBomModule: Boolean
    get() = path == ":kotlin-faker-bom"
val Project.isCoreModule: Boolean
    get() = path == ":kotlin-faker"
val Project.isFakerModule: Boolean
    get() = !isBomModule && path.startsWith(":faker")
val Project.isExtensionModule: Boolean
    get() = path.startsWith(":extension")

// endregion

// region versioning
val Project.isDev: Provider<Boolean>
    get() = provider { version.toString() == "0.0.0" }

val Project.isSnapshot: Provider<Boolean>
    get() = provider {
        version.toString().endsWith("SNAPSHOT")
    }

val Project.isRelease: Provider<Boolean>
    get() = provider {
        (!isDev.get() && !isSnapshot.get()) && // (tag.didWork || tag.tagExists)
            (releaseVersionRegex.matches(version.toString()) ||
                releaseCandidateVersionRegex.matches(version.toString()))
    }

// endregion
