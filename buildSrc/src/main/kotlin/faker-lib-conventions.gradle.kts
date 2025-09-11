/** Plugin for "faker libraries" */
plugins {
    id("faker-jvm-conventions")
    id("faker-pub-conventions")
}

kotlin {
    sourceSets {
        jvmTest {
            dependencies {
                runtimeOnly("ch.qos.logback:logback-core:1.3.4") {
                    version { strictly("1.3.4") /* last stable for java 8 */ }
                }
                runtimeOnly("ch.qos.logback:logback-classic:1.3.4") {
                    version { strictly("1.3.4") /* last stable for java 8 */ }
                }
                runtimeOnly("org.codehaus.groovy:groovy:3.0.24")
                // needed to be able to run tests in intellij, no idea why... (gradle tests work
                // fine from cli)
                // clearly a bug with idea...
                // maybe something related to https://youtrack.jetbrains.com/issue/IDEA-163411
                runtimeOnly(libs.bundles.jackson)
            }
        }
    }
}
