plugins { `faker-ext-conventions` }

kotlin {
    sourceSets {
        val jvmMain by getting {
            dependencies {
                compileOnly(projects.kotlinFaker)
                compileOnly(libs.test.kotest.property)
            }
        }
        val jvmTest by getting {
            dependencies {
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
