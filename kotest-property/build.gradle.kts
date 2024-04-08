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
//    implementation(projects.utils.kotlinPoet)
//    implementation(projects.kopykatAnnotations)
    api(libs.bundles.kotlinpoet)
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
