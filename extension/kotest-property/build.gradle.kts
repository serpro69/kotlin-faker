plugins { `faker-ext-conventions` }

kotlin {
    sourceSets {
        val jvmMain by getting {
            dependencies {
                compileOnly(projects.core)
                compileOnly(libs.test.kotest.property)
            }
        }
        val jvmTest by getting {
            dependencies {
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
