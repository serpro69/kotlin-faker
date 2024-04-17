plugins {
    `faker-ext-conventions`
}

dependencies {
    // used in FakerArb annotation (compileOnly so that we don't bring the transitive dependency)
    compileOnly(projects.core)
    implementation(libs.test.kotest.property)
    // test
    testImplementation(projects.core) // needed for tests since we have compileOnly dependency
    testImplementation(libs.bundles.test.kotest)
}

tasks.test {
    useJUnitPlatform()
    dependsOn(":core:shadowJar")
    dependsOn(":faker:books:shadowJar")
    dependsOn(":faker:edu:shadowJar")
}
