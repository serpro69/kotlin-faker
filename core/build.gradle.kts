import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm")
    id("org.jetbrains.dokka") version "1.4.32"
    `maven-publish`
    signing
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.12.3")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.12.3")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.3")
    implementation("org.yaml:snakeyaml:1.27")
    shadow(kotlin("stdlib-jdk8"))
    shadow(kotlin("reflect"))
    shadow("org.slf4j:slf4j-api:1.7.30")
    shadow("com.github.mifmif:generex:1.0.2")
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
    archiveBaseName.set(rootProject.name)
    archiveClassifier.set("")
    relocate("com.fasterxml", "faker.com.fasterxml")
    relocate("org.yaml", "faker.org.yaml")
    dependencies {
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
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

testlogger {
    showPassed = false
    theme = com.adarshr.gradle.testlogger.theme.ThemeType.MOCHA
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
    sign(publishing.publications["fakerCore"])
}

tasks {
    assemble {
        dependsOn(shadowJar)
    }
}
