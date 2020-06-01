import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm")
    id("com.github.johnrengelman.shadow") version "5.2.0"
    application
    id("com.palantir.graal") version "0.7.0-2-g4a93d00"
}

val mainFunction = "io.github.serpro69.kfaker.app.KFakerKt"
val mainAppClass = "io.github.serpro69.kfaker.app.KFaker"

dependencies {
    implementation(project(":core"))
    implementation("info.picocli:picocli:4.3.2")
}

application {
    mainClassName = mainFunction
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

testlogger {
    showPassed = false
    theme = com.adarshr.gradle.testlogger.theme.ThemeType.MOCHA
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
    dependsOn(
        project.configurations.runtimeClasspath
//        generateGraalReflectionConfig,
//        generateGraalDynamicProxyConfig,
//        generateGraalResourceConfig
    )
}

graal {
    graalVersion("20.1.0")
    javaVersion("8")
    mainClass(mainFunction)
    outputName("kFaker")
    option("--no-fallback")
    option("--no-server")
//    option("--allow-incomplete-classpath")
    option("--report-unsupported-elements-at-runtime")
}

tasks {
    nativeImage {
        dependsOn(shadowJar)
    }
}
