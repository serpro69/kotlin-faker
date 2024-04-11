plugins {
    kotlin("jvm")
}

group = "io.github.serpro69"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(projects.extension.kotestProperty)
    implementation(libs.ksp)
    api(libs.bundles.kotlinpoet)
    implementation(libs.commons.io)
    // test
    testImplementation(kotlin("test"))
    testImplementation(libs.test.junit.jupiter.params)
}

tasks.test {
    useJUnitPlatform()
}
