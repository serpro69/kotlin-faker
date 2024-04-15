import com.google.devtools.ksp.gradle.KspTaskJvm

plugins {
    kotlin("jvm")
    id("com.google.devtools.ksp") version "1.9.21-1.0.15"
}

val fakers = listOf(
    "books",
    "commerce",
    "creatures",
    "databases",
    "edu",
    "games",
    "humor",
    "japmedia",
    "lorem",
    "misc",
    "movies",
    "music",
    "sports",
    "tech",
    "travel",
    "tvshows",
)

configurations {
    create("integrationImplementation") { extendsFrom(configurations.getByName("testImplementation")) }
    create("integrationRuntimeOnly") { extendsFrom(configurations.getByName("testRuntimeOnly")) }
    create("kspIntegration") { extendsFrom(configurations.getByName("kspTest")) }
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
    testImplementation(libs.kotlin.stdlib.jdk8)
    testImplementation(libs.ksp)
    testImplementation(projects.core)
    testImplementation(projects.faker.books)
    testImplementation(projects.faker.edu)
    testImplementation(projects.extension.kotestProperty)
    testImplementation(libs.bundles.test.kotest)
    kspTest(projects.extension.kotestProperty)
    kspTest(projects.extension.kotestPropertyKsp)
    kspTest(projects.core)
    kspTest(projects.faker.books)
    kspTest(projects.faker.edu)
    // integrationTest
    val integrationImplementation by configurations
    val kspIntegration by configurations
    integrationImplementation(project(path = ":core", configuration = "shadow"))
    kspIntegration(projects.core)
    fakers.forEach {
        integrationImplementation(project(path = ":faker:$it", configuration = "shadow"))
        kspIntegration(project(":faker:$it"))
    }
}

tasks.test {
    dependsOn(":core:shadowJar")
    dependsOn(":faker:books:shadowJar")
    dependsOn(":faker:edu:shadowJar")
    useJUnitPlatform()
}

val integrationTest by tasks.creating(Test::class) {
    testClassesDirs = sourceSets["integration"].output.classesDirs
    classpath = sourceSets["integration"].runtimeClasspath
    dependsOn(tasks.test)
    dependsOn(":core:shadowJar")
    fakers.forEach { dependsOn(":faker:$it:shadowJar") }
}

tasks.withType(KspTaskJvm::class.java).configureEach {
    dependsOn(":core:shadowJar")
    fakers.forEach { dependsOn(":faker:$it:shadowJar") }
    dependsOn(":extension:kotest-property-ksp:shadowJar")
}

// disable the default jar task
tasks.jar {
    enabled = false
}

// never publish
tasks.withType<PublishToMavenRepository> {
    enabled = false
}
