plugins { `faker-ext-conventions` }

dependencies {
    compileOnly(projects.core)
    implementation(libs.bundles.jackson)
    testImplementation(projects.core) // needed for tests since we have compileOnly dependency
    testImplementation(libs.bundles.test.kotest)
}

tasks.test {
    useJUnitPlatform()
    dependsOn(":core:shadowJar")
}
