plugins {
    kotlin("jvm")
}

group = "io.github.serpro69"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.ksp)
//    implementation(projects.utils.kotlinPoet)
//    implementation(projects.kopykatAnnotations)
    implementation(libs.commons.io)
    // test
//    testImplementation(projects.utils.compiletesting)
    testImplementation(kotlin("test"))
    testImplementation(libs.test.junit.jupiter.params)
//    testRuntimeOnly(projects.kopykatKsp)
}

tasks.test {
    useJUnitPlatform()
}
