import Yaml_to_json_gradle.Yaml2JsonPlugin
import Yaml_to_json_gradle.Yaml2JsonPluginExtension
import com.adarshr.gradle.testlogger.theme.ThemeType
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

//import io.github.serpro69.YamlToJsonPlugin

plugins {
    kotlin("jvm")
    id("org.jetbrains.dokka") version "1.7.20"
    `maven-publish`
    signing
    `yaml-to-json`
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.4.2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.4")
    implementation("org.yaml:snakeyaml:1.33")
    shadow("com.ibm.icu:icu4j:71.1")
    shadow(kotlin("stdlib-jdk8"))
    shadow(kotlin("reflect"))
    shadow("org.slf4j:slf4j-api:2.0.3")
    shadow("com.github.mifmif:generex:1.0.2")
}

apply<Yaml2JsonPlugin>() // this shouldn't really be needed since the plugin is supposed to be applied in the plugins{} block
configure<Yaml2JsonPluginExtension> {
    input.set(File("core/src/main/resources/locales"))
    output.set(File("core/build/generated/src/main/resources"))
}

tasks.processResources.get().dependsOn(tasks["yaml2json"])

configurations {
    create("integrationImplementation") { extendsFrom(testImplementation.get()) }
    create("integrationRuntimeOnly") { extendsFrom(testRuntimeOnly.get()) }
}

sourceSets {
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

val integrationTest by tasks.creating(Test::class) {
    testClassesDirs = sourceSets["integration"].output.classesDirs
    classpath = sourceSets["integration"].runtimeClasspath
    dependsOn(tasks.test)
}

tasks.withType<Jar> {
    archiveBaseName.set(rootProject.name)

    manifest {
        attributes(
            mapOf(
                "Implementation-Title" to project.name,
                "Implementation-Version" to project.version,
                "Class-Path" to configurations.compileClasspath.get().joinToString(" ") { it.name }
            )
        )
    }
}

val shadowJar by tasks.getting(ShadowJar::class) {
    minimize()
    archiveBaseName.set(rootProject.name)
    archiveClassifier.set("")
    relocate("com.fasterxml", "faker.com.fasterxml")
    relocate("org.yaml", "faker.org.yaml")
    exclude("**/locales/*.yml") // jar already contains json files
    dependencies {
        exclude("module-info.class")
        include {
            it.name.startsWith(project.group.toString()) ||
                it.name.startsWith("com.fasterxml") ||
                it.name.startsWith("org.yaml")
        }
    }
    manifest {
        attributes(
            mapOf(
                "Implementation-Title" to project.name,
                "Implementation-Version" to project.version,
                "Class-Path" to project.configurations.compileClasspath.get().joinToString(" ") { it.name }
            )
        )
    }
    dependsOn(tasks.jar)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

testlogger {
    showPassed = false
    theme = ThemeType.MOCHA
}

val sourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.getByName("main").allSource)
    from("LICENCE.md") {
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

val artifactName = rootProject.name
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
val pomDeveloperName = "Sergii Prodan"

publishing {
    publications {
        create<MavenPublication>("fakerCore") {
            groupId = artifactGroup
            artifactId = artifactName
            version = artifactVersion
//            from(components["java"])
            project.shadow.component(this)
            artifact(sourcesJar)
            artifact(dokkaJavadocJar) //TODO configure dokka or use defaults?

            pom {
                packaging = "jar"
                name.set(rootProject.name)
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
        sign(publishing.publications["fakerCore"])
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
