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
                implementation(projects.core) // needed for tests since we have compileOnly dependency
                implementation(libs.bundles.test.kotest)
            }
        }
    }
}

tasks.jvmTest {
    useJUnitPlatform()
    dependsOn(":core:jvmJar")
}
