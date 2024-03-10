import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
}

dependencies {
    val implementation by configurations
    implementation(project(":core"))
}

// since we're adding :core as implementation dependency,
// we also need to make sure ShadowJar tasks depend on core having been built
val shadowJar by tasks.getting(ShadowJar::class) {
    dependsOn(":core:assemble")
}

// since we're adding :core as implementation dependency, and effectively testImplementation
// we also need to make sure Test tasks depend on core having been built
tasks.withType<Test> {
    dependsOn(":core:assemble")
}
