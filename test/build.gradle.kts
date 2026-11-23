import io.github.serpro69.semverkt.gradle.plugin.tasks.TagTask
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

plugins {
    java
    kotlin("jvm")
}

dependencies {
    testImplementation(libs.bundles.kotlin)
    testImplementation(project(path = ":kotlin-faker"))
    testImplementation(libs.bundles.test.kotest)
}

val jvmCompilerVersion = libs.versions.jvmCompiler.map { JavaLanguageVersion.of(it) }
val jvmMinTargetVersion = libs.versions.jvmMinTarget

configure<JavaPluginExtension> {
    toolchain {
        languageVersion.set(jvmCompilerVersion)
    }
}

configure<KotlinJvmProjectExtension> {
    jvmToolchain {
        languageVersion.set(jvmCompilerVersion)
    }
    // Provider tests consume this JAR on the minimum supported JVM, even with a newer compiler JDK.
    compilerOptions {
        jvmTarget.set(jvmMinTargetVersion.map { JvmTarget.fromTarget(it) })
        freeCompilerArgs.add(jvmMinTargetVersion.map { "-Xjdk-release=$it" })
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.release.set(jvmMinTargetVersion.map { it.toInt() })
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
