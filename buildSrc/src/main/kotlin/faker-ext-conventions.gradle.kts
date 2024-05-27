import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    base
    kotlin("jvm")
    id("org.jetbrains.dokka")
    id("faker-pub-conventions")
}

configurations {
    create("integrationImplementation") { extendsFrom(configurations.getByName("testImplementation")) }
    create("integrationRuntimeOnly") {
        extendsFrom(
            configurations.getByName("testRuntimeOnly"),
        )
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

dependencies {
    val implementation by configurations
    implementation(libs.bundles.kotlin)
}

val integrationTest by tasks.creating(Test::class) {
    testClassesDirs = sourceSets["integration"].output.classesDirs
    classpath = sourceSets["integration"].runtimeClasspath
    dependsOn(tasks.test)
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

    dependsOn(integrationTest)
}

artifacts {
    archives(sourcesJar)
    archives(dokkaJavadocJar)
}

tasks {
    assemble {
        dependsOn(integrationTest)
        dependsOn(jar)
    }
}

tasks.withType<DokkaTask>().configureEach {
    onlyIf("Not dev") { !isDev.get() }
    onlyIf("Release or snapshot") { isRelease.get() || isSnapshot.get() }
}
