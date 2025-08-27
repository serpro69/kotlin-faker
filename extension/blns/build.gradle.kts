plugins { `faker-ext-conventions` }

kotlin {
    sourceSets {
        val jvmMain by getting {
            dependencies {
                compileOnly(projects.core)
                implementation(libs.bundles.jackson)
            }
        }
        val jvmTest by getting {
            dependencies {
                // needed for tests since we have compileOnly dependency
                implementation(projects.core)
                implementation(libs.bundles.test.kotest)
            }
        }
    }
}

tasks.jvmTest {
    useJUnitPlatform()
    dependsOn(":core:jvmJar")
}
