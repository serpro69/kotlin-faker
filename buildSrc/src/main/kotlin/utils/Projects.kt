import io.github.serpro69.semverkt.gradle.plugin.tasks.TagTask
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.getByName
import org.gradle.kotlin.dsl.the

// region manually define accessors, because IntelliJ _still_ doesn't index them properly :(
val Project.libs: LibrariesForLibs
    get() = the<LibrariesForLibs>()

// endregion

val Project.isBomModule: Boolean
    get() = path.startsWith(":bom")
val Project.isCoreModule: Boolean
    // TODO: is this correct after I've renamed the module? does path change?
    get() = path.startsWith(":core")
val Project.isFakerModule: Boolean
    get() = path.startsWith(":faker")
val Project.isExtensionModule: Boolean
    get() = path.startsWith(":extension")

// region versioning
val Project.isDev: Provider<Boolean>
    get() = provider { version.toString().startsWith("0.0.0") }

val Project.isSnapshot: Provider<Boolean>
    get() = provider {
        // QUESTION do we need to check if rootProject is also set to snapshot?
        //  Likely not, since "isRelease" will not just check for the version, but also for the
        // actual tag creation
        //    rootProject.version.toString().endsWith("SNAPSHOT") &&
        version.toString().endsWith("SNAPSHOT")
    }
val Project.isRelease: Provider<Boolean>
    get() = provider {
        val tag = tasks.getByName("tag", TagTask::class)
        (!isDev.get() && !isSnapshot.get()) && (tag.didWork || tag.tagExists)
    }

// endregion
