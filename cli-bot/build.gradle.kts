import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    application
    kotlin("jvm")
    id("org.graalvm.buildtools.native") version "0.10.1"
    id("com.github.johnrengelman.shadow")
}

val mainFunction = "io.github.serpro69.kfaker.app.KFakerKt"
val mainAppClass = "io.github.serpro69.kfaker.app.KFaker"

val fakers = listOf(
    "books",
    "commerce",
    "creatures",
    "databases",
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

dependencies {
    implementation(project(path = ":core", configuration = "shadow"))
    fakers.forEach { implementation(project(path = ":faker:$it", configuration = "shadow")) }
    implementation("info.picocli:picocli:4.7.5")
    testImplementation(libs.bundles.test.kotest)
}

// Test tasks must run after we've built the dependencies
tasks.withType<Test> {
    dependsOn(":core:shadowJar")
    fakers.forEach { dependsOn(":faker:$it:shadowJar") }
}

application {
//    mainClassName = mainFunction
    mainClass.set(mainFunction)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(8)
    }
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
    // since we're adding :core and :faker:* as implementation dependencies
    // we also need to make sure ShadowJar task depend on core having been built
    dependsOn(":core:shadowJar")
    fakers.forEach { dependsOn(":faker:$it:shadowJar") }
}

// dunno why, but gradle is complaining that 'startScripts' may run before dependencies have been built
tasks.startScripts {
    dependsOn(":core:shadowJar")
    fakers.forEach { dependsOn(":faker:$it:shadowJar") }
}

graalvmNative {
    testSupport = false
    toolchainDetection = true
    binaries {
        named("main") {
            imageName = "faker-bot_${project.version}"
            javaLauncher = javaToolchains.launcherFor {
                languageVersion = JavaLanguageVersion.of(17)
                vendor.set(JvmVendorSpec.GRAAL_VM)
            }
            mainClass.set(mainFunction)
        }
    }
}

//graalvmNative {
//    graalVersion("21.2.0")
//    javaVersion("8")
//    mainClass(mainFunction)
//    outputName("faker-bot_${project.version}")
//    option("--no-fallback")
//    option("--no-server")
//    option("--report-unsupported-elements-at-runtime")
//}

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

    nativeCompile {
        dependsOn(shadowJar)
    }

    generateResourcesConfigFile {
        fakers.forEach { dependsOn(":faker:$it:shadowJar") }
    }
}
