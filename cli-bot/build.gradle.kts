import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import com.adarshr.gradle.testlogger.theme.ThemeType

plugins {
    application
    kotlin("jvm")
    id("com.palantir.graal") version "0.10.0" // 0.12.0+ requires java11+
}

val mainFunction = "io.github.serpro69.kfaker.app.KFakerKt"
val mainAppClass = "io.github.serpro69.kfaker.app.KFaker"

dependencies {
    val providers = listOf(
        "books",
        "commerce",
        "creature",
        "edu",
        "games",
        "humor",
        "japmedia",
        "lorem",
        "misc",
        "movies",
        "music",
        "sports",
        "tech",
        "travel",
        "tvshows",
    )

    implementation(project(":core"))
    providers.forEach { implementation(project(":provider:$it")) }
    implementation("info.picocli:picocli:4.7.5")
}

application {
//    mainClassName = mainFunction
    mainClass.set(mainFunction)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

testlogger {
    showPassed = false
    theme = ThemeType.MOCHA
}

val shadowJar by tasks.getting(ShadowJar::class) {
    manifest {
        attributes(
            mapOf(
                "Implementation-Title" to project.name,
                "Implementation-Version" to project.version,
                "Class-Path" to project.configurations.compileClasspath.get().joinToString(" ") { it.name },
                "Main-Class" to mainFunction
            )
        )
    }

    archiveClassifier.set("fat")
    from(sourceSets.main.get().output)
//    from(project.configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    from(project.configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) })
    with(tasks.jar.get() as CopySpec)
    dependsOn(project.configurations.runtimeClasspath)
}

graal {
    graalVersion("21.2.0")
    javaVersion("8")
    mainClass(mainFunction)
    outputName("faker-bot_${project.version}")
    option("--no-fallback")
    option("--no-server")
    option("--report-unsupported-elements-at-runtime")
}

tasks {
    compileKotlin {
        // Set version for --version options
        doFirst("Set app version") {
            val command = "find . -type f -name 'KFaker.kt' -exec sed -i 's/{FAKER_VER}/${project.version}/g' {} +;"

            exec {
                commandLine("sh", "-c", command)
            }
        }

        // Restore the file so it's not accidentally committed
        doLast {
            exec {
                commandLine("sh", "-c", "git checkout *KFaker.kt")
            }
        }
        dependsOn(":core:assemble")
    }

    nativeImage {
        dependsOn(shadowJar)
    }
}
