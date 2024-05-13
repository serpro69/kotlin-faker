import com.github.jengelman.gradle.plugins.shadow.ShadowExtension
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import io.github.serpro69.semverkt.gradle.plugin.tasks.TagTask
import org.gradle.accessors.dm.LibrariesForLibs
import org.jetbrains.dokka.gradle.DokkaTask
import java.util.*

plugins {
    base
    `java-library`
    kotlin("jvm")
    id("org.jetbrains.dokka")
    id("com.github.johnrengelman.shadow")
    `maven-publish`
    signing
}

val libs = the<LibrariesForLibs>()

/**
 * For additional providers, use a combination of rootProject and subproject names for artifact name and similar things.
 * i.e. kotlin-faker-books, kotlin-faker-movies, kotlin-faker-tv, ...
 *
 * The "core" lib retains the same name as before: kotlin-faker
 */
private val fullName: String =
    if (project.name == "core") rootProject.name else "${rootProject.name}-${project.name}"

val isDev = provider { version.toString().startsWith("0.0.0") }
val isSnapshot = provider {
    // QUESTION do we need to check if rootProject is also set to snapshot?
    //  Likely not, since "isRelease" will not just check for the version, but also for the actual tag creation
    //    rootProject.version.toString().endsWith("SNAPSHOT") &&
        version.toString().endsWith("SNAPSHOT")
}
val isRelease = provider {
    val tag = project.tasks.getByName("tag", TagTask::class)
    /* all fakers have their own tags, so checking if tag.didWork is enough for them,
       ':core' shares the tag with 'root', ':bom' and ':cli-bot' modules,
       and hence the tag might already exist and didWork will return false for ':core' */
    val tagCreated = if (project.name != "core") tag.didWork else tag.didWork || tag.tagExists
    !isDev.get() && !isSnapshot.get() && tagCreated
}

configurations {
    create("integrationImplementation") { extendsFrom(configurations.getByName("testImplementation")) }
    create("integrationRuntimeOnly") {
        extendsFrom(
            configurations.getByName("testRuntimeOnly"),
            configurations.getByName("shadow")
        )
    }
}

// configure sourceSets as extension since it's not available here as `sourceSets` is an extension on `Project`
// https://docs.gradle.org/current/userguide/kotlin_dsl.html#project_extensions_and_conventions
configure<SourceSetContainer> {
    create("integration") {
        resources.srcDir("src/integration/resources")
        compileClasspath += main.get().compileClasspath + test.get().compileClasspath
        runtimeClasspath += main.get().runtimeClasspath + test.get().runtimeClasspath
    }
    main {
        resources {
            this.srcDir("build/generated/src/main/resources")
        }
    }
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

val integrationTest by tasks.creating(Test::class) {
    testClassesDirs = sourceSets["integration"].output.classesDirs
    classpath = sourceSets["integration"].runtimeClasspath
    dependsOn(tasks.test)
}

tasks.withType<Jar> {
    archiveBaseName.set(fullName)

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
    dependsOn(integrationTest)
    dependsOn(tasks.jar)
}

val sourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.getByName("main").allSource)
    from("${rootProject.rootDir.resolve("LICENSE.adoc")}") {
        into("META-INF")
    }
}

val dokkaJavadocJar by tasks.creating(Jar::class) {
    archiveClassifier.set("javadoc")
    dependsOn(tasks.dokkaJavadoc)
    from(tasks.dokkaJavadoc.get().outputDirectory.orNull)
}

artifacts {
    archives(sourcesJar)
    archives(dokkaJavadocJar)
}

val publicationName =
    "faker${project.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }}"

publishing {
    publications {
        create<MavenPublication>(publicationName) {
            groupId = project.group.toString()
            artifactId = fullName
            version = project.version.toString()
//            from(components["java"])
            ShadowExtension(project).component(this)
            artifact(sourcesJar)
            artifact(dokkaJavadocJar) //TODO configure dokka or use defaults?

            pom {
                packaging = "jar"
                name.set(fullName)
                description.set("Generate realistically looking fake data such as names, addresses, banking details, and many more, that can be used for testing and data anonymization purposes.")
                url.set("https://github.com/serpro69/kotlin-faker")
                scm {
                    connection.set("scm:git:https://github.com/serpro69/kotlin-faker")
                    developerConnection.set("scm:git:https://github.com/serpro69")
                    url.set("https://github.com/serpro69/kotlin-faker")
                }
                issueManagement {
                    url.set("https://github.com/serpro69/kotlin-faker/issues")
                }
                licenses {
                    license {
                        name.set("MIT")
                        url.set("https://opensource.org/licenses/mit-license.php")
                    }
                }
                developers {
                    developer {
                        id.set("serpro69")
                        name.set("Serhii Prodan")
                    }
                }
            }
        }
    }
}

tasks {
    assemble {
        dependsOn(shadowJar)
    }
}

signing {
    sign(publishing.publications[publicationName])
}

tasks.withType<DokkaTask>().configureEach {
    onlyIf("Not dev") { !isDev.get() }
    onlyIf("Release or snapshot") { isRelease.get() || isSnapshot.get() }
}

tasks.withType<PublishToMavenRepository>().configureEach {
    dependsOn(project.tasks.getByName("tag"))
    dependsOn(project.tasks.withType(Sign::class.java))
    onlyIf("Not dev") { !isDev.get() }
    onlyIf("Release or snapshot") { isRelease.get() || isSnapshot.get() }
}

tasks.withType<PublishToMavenLocal>().configureEach {
    onlyIf("In development") { isDev.get() || isSnapshot.get() }
}

tasks.withType<Sign>().configureEach {
    dependsOn(project.tasks.getByName("tag"))
    onlyIf("Not dev and snapshot") { !isDev.get() && !isSnapshot.get() }
    onlyIf("Release") { isRelease.get() }
}
