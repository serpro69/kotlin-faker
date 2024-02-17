
plugins {
}

dependencies {
    val compileOnly by configurations
    val testImplementation by configurations
    // In order to use an additional fake data provider,
    // core faker needs to be on the classpath.
    // Don't add it as transitive dependency to each faker provider
    compileOnly(project(":core"))
    // we need implementation dependency for tests to be able to access 'core' functionality
    testImplementation(project(":core"))
}

// since we're adding :core as testImplementation dependency,
// we also need to make sure Test tasks depend on core having been built
tasks.withType<Test> {
    dependsOn(":core:shadowJar")
}

