
plugins {
}

dependencies {
    val compileOnly by configurations
    val testImplementation by configurations
    compileOnly(project(":core"))
    // we need implementation dependency for tests to be able to access 'core' functionality
    testImplementation(project(":core"))
}

