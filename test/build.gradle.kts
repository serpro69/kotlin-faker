import io.github.serpro69.semverkt.gradle.plugin.tasks.TagTask

plugins {
}

dependencies {
    testImplementation(project(path = ":core", configuration = "shadow"))
}

configurations.create("testHelper")

val testJar = tasks.create("testJar", Jar::class) {
    archiveClassifier.set("test")
    from(sourceSets.test.get().output)
    dependsOn(tasks.testClasses)
}

artifacts {
    val testHelper by configurations
    add(testHelper.name, testJar)
}

// disable the default jar task
tasks.jar {
    enabled = false
}

// never publish
tasks.withType<PublishToMavenRepository> {
    enabled = false
}

// nothing to test in this module yet,
// and we use test sources to produce artifacts...
tasks.withType<Test> {
    enabled = false
}

tasks.withType<TagTask> {
    enabled = false
}
