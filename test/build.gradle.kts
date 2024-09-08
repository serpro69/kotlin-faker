import io.github.serpro69.semverkt.gradle.plugin.tasks.TagTask
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

plugins {
    java
    kotlin("jvm")
}

dependencies {
    testImplementation(project(path = ":core", configuration = "shadow"))
    testImplementation(libs.bundles.test.kotest)
}

configure<JavaPluginExtension> {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

configure<KotlinJvmProjectExtension> {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
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

// never publish
tasks.withType<PublishToMavenRepository> { enabled = false }
// nothing to test in this module yet,
// and we use test sources to produce artifacts...
tasks.withType<Test> { enabled = false }
// disable tag
tasks.withType<TagTask> { enabled = false }
