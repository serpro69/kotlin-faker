plugins {
    kotlin("jvm")
    id("com.github.johnrengelman.shadow") version "5.2.0"
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":core"))
    implementation("info.picocli:picocli:4.2.0")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

testlogger {
    showPassed = false
    theme = com.adarshr.gradle.testlogger.theme.ThemeType.MOCHA
}

allure {
    version = "2.8.1"
    aspectjweaver = false
    aspectjVersion = "1.9.5"
    autoconfigure = true
    allureJavaVersion = "2.13.3"
    useJUnit5 {
        version = "2.13.3"
    }
}

/*val shadowJar by tasks.getting(com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar::class) {
    manifest {
        attributes(
            mapOf(
                "Implementation-Title" to project.name,
                "Implementation-Version" to project.version,
                "Class-Path" to project.configurations.compileClasspath.get().joinToString(" ") { it.name },
                "Main-Class" to "io.github.serpro69.kfaker.app.KFakerKt"
            )
        )
    }

    archiveBaseName.set("${project.name}-fat")

    from(project.configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    with(tasks.jar.get() as CopySpec)
}*/

/*
tasks {
    build {
        dependsOn(shadowJar)
    }
}
*/
