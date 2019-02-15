import org.jetbrains.kotlin.gradle.tasks.*

plugins {
    kotlin("jvm") version "1.3.21"
}

group = "com.github.sergio-igwt"
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
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.2.1")
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
}
