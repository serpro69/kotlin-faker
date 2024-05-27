import gradle.kotlin.dsl.accessors._617ff5292df7551646490c1442241820.assemble
import gradle.kotlin.dsl.accessors._617ff5292df7551646490c1442241820.jar
import gradle.kotlin.dsl.accessors._617ff5292df7551646490c1442241820.sourceSets
import gradle.kotlin.dsl.accessors._617ff5292df7551646490c1442241820.test
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.creating
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.withType
import org.jetbrains.dokka.gradle.DokkaTask

/**
 * Plugin for base build setup of faker modules with kotlin
 */

plugins {
    base
    kotlin("jvm")
    id("org.jetbrains.dokka")
}

configurations {
    create("integrationImplementation") { extendsFrom(configurations.getByName("testImplementation")) }
    create("integrationRuntimeOnly") {
        if (isShadow) {
            extendsFrom(
                configurations.getByName("testRuntimeOnly"),
                configurations.getByName("shadow"),
            )
        } else {
            extendsFrom(configurations.getByName("testRuntimeOnly"))
        }
    }
}

// configure sourceSets as extension since it's not available here as `sourceSets` is an extension on `Project`
// https://docs.gradle.org/current/userguide/kotlin_dsl.html#project_extensions_and_conventions
configure<SourceSetContainer> {
    create("integration") {
        resources.srcDir("src/integration/resources")
        compileClasspath += main.get().compileClasspath + test.get().compileClasspath
        runtimeClasspath += main.get().runtimeClasspath + test.get().runtimeClasspath
    }
    main {
        resources {
            this.srcDir("build/generated/src/main/resources")
        }
    }
}

val integrationTest: Test by tasks.creating(Test::class) {
    testClassesDirs = sourceSets["integration"].output.classesDirs
    classpath = sourceSets["integration"].runtimeClasspath
}

tasks.withType<Jar> {
    archiveBaseName.set(fullName)

    manifest {
        attributes(
            mapOf(
                "Implementation-Title" to fullName,
                "Implementation-Version" to project.version,
                /*
                 * We can't add this here because this resolves the configuration,
                 * after which it effectively becomes read-only and we'll get an error
                 * Cannot change dependencies of dependency configuration ':core:implementation' after it has been included in dependency resolution
                 * if we try to add more dependencies in the module's build.gradle file directly
                 */
                // "Class-Path" to project.configurations.compileClasspath.get().joinToString(" ") { it.name }
            )
        )
    }
}

tasks {
    getByName(integrationTest.name).dependsOn(test)
    jar { dependsOn(integrationTest) }
    assemble { dependsOn(jar) }
}

tasks.withType<DokkaTask>().configureEach {
    onlyIf("Not dev") { !isDev.get() }
    onlyIf("Release or snapshot") { isRelease.get() || isSnapshot.get() }
}
