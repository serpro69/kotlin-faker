plugins { `faker-ext-conventions` }

kotlin {
    sourceSets {
        val jvmMain by getting {
            dependencies {
                compileOnly(projects.kotlinFaker)
                implementation(libs.bundles.jackson)
            }
        }
        val jvmTest by getting {
            dependencies {
                // needed for tests since we have compileOnly dependency
                implementation(projects.kotlinFaker)
                implementation(libs.bundles.test.kotest)
            }
        }
    }
}

tasks.jvmTest {
    useJUnitPlatform()
    dependsOn(":kotlin-faker:jvmJar")
}
