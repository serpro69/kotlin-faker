plugins {
    `faker-ext-conventions`
}

dependencies {
    compileOnly(projects.extension.kotestProperty)
    implementation(libs.ksp)
    api(libs.bundles.kotlinpoet)
}

tasks.test {
    useJUnitPlatform()
}
