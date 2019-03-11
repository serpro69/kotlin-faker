import org.jetbrains.kotlin.gradle.tasks.*

plugins {
    kotlin("jvm") version "1.3.21"
}

group = "io.github.serpro69"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("com.fasterxml.jackson.core:jackson-databind:2.9.8")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.9.8")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.8")
    implementation("com.github.mifmif:generex:1.0.2")
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.2.1")
    runtime(kotlin("script-runtime"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Wrapper> {
    this.gradleVersion = "5.2.1"
    this.distributionType = Wrapper.DistributionType.ALL
}

val test by tasks.getting(Test::class) {
    useJUnitPlatform {}

    // show standard out and standard error of the test JVM(s) on the console
    testLogging.showStandardStreams = true

    // Always run tests, even when nothing changed.
    dependsOn("cleanTest")

    testLogging {
        events("PASSED", "FAILED", "SKIPPED", "STANDARD_OUT", "STANDARD_ERROR")
    }
}
