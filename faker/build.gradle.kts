import io.github.serpro69.semverkt.gradle.plugin.tasks.TagTask
import org.gradle.jvm.tasks.Jar

plugins {
}

// no sources for this module
sourceSets {
    main.configure {
        java { setSrcDirs(emptySet<String>()) }
        kotlin { setSrcDirs(emptySet<String>()) }
        resources { setSrcDirs(emptySet<String>()) }
    }
    test.configure {
        java { setSrcDirs(emptySet<String>()) }
        kotlin { setSrcDirs(emptySet<String>()) }
        resources { setSrcDirs(emptySet<String>()) }
    }
}

// disable api validation tasks
tasks.apiBuild { enabled = false }
tasks.apiCheck { enabled = false }
tasks.apiDump { enabled = false }
// disable the default jar task
tasks.withType<Jar> { enabled = false }
// never publish
tasks.withType<PublishToMavenRepository> { enabled = false }
// nothing to test in this module
tasks.withType<Test> { enabled = false }
// disable tag
tasks.withType<TagTask> { enabled = false }
