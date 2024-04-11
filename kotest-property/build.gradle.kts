plugins {
    kotlin("jvm")
}

group = "io.github.serpro69"

repositories {
    mavenCentral()
}

dependencies {
    // used in FakerArb annotation (compileOnly so that we don't bring the transitive dependency)
    compileOnly(projects.core)
    implementation(libs.ksp)
    implementation(libs.test.kotest.property)
    api(libs.bundles.kotlinpoet)
    implementation(libs.commons.io)
    // test
    testImplementation(kotlin("test"))
    testImplementation(libs.test.junit.jupiter.params)
}

tasks.test {
    useJUnitPlatform()
}
