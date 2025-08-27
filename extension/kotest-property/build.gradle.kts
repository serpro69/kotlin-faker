plugins { `faker-ext-conventions` }

dependencies {
    compileOnly(projects.core)
    compileOnly(libs.test.kotest.property)
    // test
    testImplementation(projects.core)
    testImplementation(libs.bundles.test.kotest)
}

tasks.test {
    useJUnitPlatform()
    dependsOn(":core:jar")
}
