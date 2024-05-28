import com.github.jengelman.gradle.plugins.shadow.ShadowExtension
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.dokka.gradle.DokkaTask

/**
 * Plugin for "faker libraries"
 */

plugins {
    `java-library`
    id("org.jetbrains.dokka")
    id("com.github.johnrengelman.shadow")
    id("faker-base-conventions")
    id("faker-pub-conventions")
}

dependencies {
    val shadow by configurations
    val implementation by configurations
    val testImplementation by configurations
    val testRuntimeOnly by configurations
    val integrationImplementation by configurations
    shadow(libs.bundles.kotlin)
    testRuntimeOnly("ch.qos.logback:logback-core:1.3.4") {
        version { strictly("1.3.4") /* last stable for java 8 */ }
    }
    testRuntimeOnly("ch.qos.logback:logback-classic:1.3.4") {
        version { strictly("1.3.4") /* last stable for java 8 */ }
    }
    testRuntimeOnly("org.codehaus.groovy:groovy:3.0.19")
    // we're shadowing these, so they need to be available for test runtime
    testRuntimeOnly(libs.icu4j)
    testRuntimeOnly(libs.rgxgen)
    // needed to be able to run tests in intellij, no idea why... (gradle tests work fine from cli)
    // clearly a bug with idea...
    // maybe something related to https://youtrack.jetbrains.com/issue/IDEA-163411
    testRuntimeOnly(libs.bundles.jackson)
}

val shadowJar by tasks.getting(ShadowJar::class) {
    minimize()
    archiveBaseName.set(fullName)
    archiveClassifier.set("")
    relocate("com.fasterxml", "faker.com.fasterxml")
    exclude("**/locales/*.yml") // jar already contains json files
    dependencies {
        exclude("module-info.class")
        include {
            it.name.startsWith(project.group.toString()) ||
                it.name.startsWith("com.fasterxml")
        }
    }
    manifest {
        attributes(
            mapOf(
                "Implementation-Title" to fullName,
                "Implementation-Version" to project.version,
                /*
                 * We can't add this here because this resolves the configuration,
                 * after which it effectively becomes read-only and we'll get an error
                 * Cannot change dependencies of dependency configuration ':core:implementation' after it has been included in dependency resolution
                 * if we try to add more dependencies in the module's build.gradle file directly
                 */
                // "Class-Path" to project.configurations.compileClasspath.get().joinToString(" ") { it.name }
            )
        )
    }
    from("${rootProject.rootDir.resolve("LICENSE.adoc")}") {
        into("META-INF")
    }
}

publishing {
    publications.withType<MavenPublication>().all {
        // For whatever reason I'm not able to use this in the faker-pub-conventions plugin
        // because it picks up a default artifact name, which I don't know how to override.
        // Might be some shadow plugin limitations
        if (isShadow) ShadowExtension(project).component(this)
        else throw GradleException("Unsupported publication: $this")
    }
}

tasks {
    getByName(shadowJar.name).dependsOn(jar)
    build { dependsOn(shadowJar) }
}
