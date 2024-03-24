import com.github.jengelman.gradle.plugins.shadow.ShadowExtension
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import java.util.*

plugins {
    `java-library`
    kotlin("jvm")
    id("org.jetbrains.dokka")
    id("com.github.johnrengelman.shadow")
    `maven-publish`
    signing
}

/**
 * For additional providers, use a combination of rootProject and subproject names for artifact name and similar things.
 * i.e. kotlin-faker-books, kotlin-faker-movies, kotlin-faker-tv, ...
 *
 * The "core" lib retains the same name as before: kotlin-faker
 */
private val fullName: String =
    if (project.name == "core") rootProject.name else "${rootProject.name}-${project.name}"

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
    shadow(kotlin("stdlib-jdk8"))
    shadow(kotlin("reflect"))
    testRuntimeOnly("ch.qos.logback:logback-core:1.3.4") {
        version { strictly("1.3.4") /* last stable for java 8 */ }
    }
    testRuntimeOnly("ch.qos.logback:logback-classic:1.3.4") {
        version { strictly("1.3.4") /* last stable for java 8 */ }
    }
    testRuntimeOnly("org.codehaus.groovy:groovy:3.0.19")
    // we're shadowing these so they need to be available for test runtime
    testRuntimeOnly("com.ibm.icu:icu4j:73.2")
    testRuntimeOnly("com.github.mifmif:generex:1.0.2")
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

val artifactName = fullName
val artifactGroup = project.group.toString()
val artifactVersion = project.version.toString()
val releaseTagName = "v$artifactVersion"

val pomUrl = "https://github.com/serpro69/kotlin-faker"
val pomScmUrl = "https://github.com/serpro69/kotlin-faker"
val pomIssueUrl = "https://github.com/serpro69/kotlin-faker/issues"
val pomDesc = "https://github.com/serpro69/kotlin-faker"

val ghRepo = "serpro69/kotlin-faker"
val ghReadme = "README.md"

val pomLicenseName = "MIT"
val pomLicenseUrl = "https://opensource.org/licenses/mit-license.php"
val pomLicenseDist = "repo"

val pomDeveloperId = "serpro69"
val pomDeveloperName = "Serhii Prodan"

val publicationName =
    "faker${project.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }}"

publishing {
    publications {
        create<MavenPublication>(publicationName) {
            groupId = artifactGroup
            artifactId = artifactName
            version = artifactVersion
//            from(components["java"])
            ShadowExtension(project).component(this)
            artifact(sourcesJar)
            artifact(dokkaJavadocJar) //TODO configure dokka or use defaults?

            pom {
                packaging = "jar"
                name.set(fullName)
                description.set(pomDesc)
                url.set(pomUrl)
                scm {
                    url.set(pomScmUrl)
                }
                issueManagement {
                    url.set(pomIssueUrl)
                }
                licenses {
                    license {
                        name.set(pomLicenseName)
                        url.set(pomLicenseUrl)
                    }
                }
                developers {
                    developer {
                        id.set(pomDeveloperId)
                        name.set(pomDeveloperName)
                    }
                }
            }
        }
    }
}

signing {
    if (!version.toString().endsWith("SNAPSHOT")) {
        sign(publishing.publications[publicationName])
    }
}

tasks {
    assemble {
        dependsOn(shadowJar)
    }
}

tasks.withType<PublishToMavenRepository>().configureEach {
    doFirst {
        if (version == "0.0.0") throw IllegalArgumentException("Unable to publish version 0.0.0")
    }
}
