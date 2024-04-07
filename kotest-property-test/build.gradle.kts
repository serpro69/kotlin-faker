plugins {
    kotlin("jvm")
    id("com.google.devtools.ksp") version "1.9.21-1.0.15"
}

group = "io.github.serpro69"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(libs.kotlin.stdlib.jdk8)
    testImplementation(libs.ksp)
    testImplementation(projects.core)
    testImplementation(projects.faker.books)
    testImplementation(projects.kotestProperty)
    testImplementation(libs.bundles.test.kotest)
    ksp(projects.kotestProperty)
}

tasks.test {
    useJUnitPlatform()
}
