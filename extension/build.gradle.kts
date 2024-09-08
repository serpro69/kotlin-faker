import io.github.serpro69.semverkt.gradle.plugin.tasks.TagTask

plugins {
}

// disable the default jar task
tasks.withType<Jar> { enabled = false }
// never publish
tasks.withType<PublishToMavenRepository> { enabled = false }
// nothing to test in this module
tasks.withType<Test> { enabled = false }
// disable tag
tasks.withType<TagTask> { enabled = false }
