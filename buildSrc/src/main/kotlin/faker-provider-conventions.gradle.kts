import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import io.github.serpro69.semverkt.spec.Semver

plugins {
}

val core = rootProject.subprojects.first { it.path == ":core" }
    ?: throw GradleException(":core project not found")

dependencies {
    val compileOnly by configurations
    val testImplementation by configurations
    val testRuntimeOnly by configurations
    val integrationImplementation by configurations
    /* :core is versioned separately,
    during development versions will always equal
      (both are set to a version placeholder via gradle.properties),
    but during publishing they might not (depending on changes to a given module)
    hence we first check the versions equality, and then
    EITHER set a dependency on a published :core artifact with latest version
      (latest is handled by semver.kt plugin for multi-tag monorepos),
    OR a project-type dependency on the :core submodule */
    // In order to use an additional fake data provider, core faker needs to be on the classpath.
    // Don't add it as transitive dependency to each faker provider
    compileOnly(project(path = ":core", configuration = "shadow"))
    // we need implementation dependency for tests to be able to access 'core' functionality
    testImplementation(project(path = ":core", configuration = "shadow"))
    // provides helpers for integration tests
    integrationImplementation(project(":test", "testHelper"))
}

// we have a dependency on :core,
// hence we also need to make sure ShadowJar tasks depend on core having been built
val shadowJar by tasks.getting(ShadowJar::class) {
    dependsOn(":core:assemble")
}

// since we're adding :core as implementation dependency, and effectively testImplementation
// we also need to make sure Test tasks depend on core having been built
tasks.withType<Test> {
    dependsOn(":core:assemble")
}
