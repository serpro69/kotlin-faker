/** Plugin for :extension:* modules */
plugins { id("faker-lib-conventions") }

kotlin {
    sourceSets {
        jvmMain {
            dependencies {
                compileOnly(project(":kotlin-faker"))
            }
        }
        jvmTest {
            dependencies {
                // needed for tests since we have compileOnly dependency
                implementation(project(":kotlin-faker"))
            }
        }
    }
}

tasks.named<Test>("jvmTest") {
    // TODO: is this needed?
    dependsOn(":kotlin-faker:jvmJar")
}
