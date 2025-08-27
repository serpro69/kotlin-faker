import org.jetbrains.dokka.gradle.DokkaTask

/**
 * Plugin for "faker libraries"
 */

plugins {
    `java-library`
    id("org.jetbrains.dokka")
    id("faker-kotlin-conventions")
    id("faker-pub-conventions")
}

dependencies {
    val implementation by configurations
    val testRuntimeOnly by configurations
    implementation(libs.bundles.kotlin)
    testRuntimeOnly("ch.qos.logback:logback-core:1.3.4") {
        version { strictly("1.3.4") /* last stable for java 8 */ }
    }
    testRuntimeOnly("ch.qos.logback:logback-classic:1.3.4") {
        version { strictly("1.3.4") /* last stable for java 8 */ }
    }
    testRuntimeOnly("org.codehaus.groovy:groovy:3.0.24")
    // needed to be able to run tests in intellij, no idea why... (gradle tests work fine from cli)
    // clearly a bug with idea...
    // maybe something related to https://youtrack.jetbrains.com/issue/IDEA-163411
    testRuntimeOnly(libs.bundles.jackson)
}
