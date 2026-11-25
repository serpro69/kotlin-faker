import groovy.json.JsonOutput

/** Root entry points automatically include modules opting in to the portable convention. */
val kmpJvmCheck by tasks.registering { group = "verification" }
val kmpJsCheck by tasks.registering { group = "verification" }
val kmpIosCheck by tasks.registering { group = "verification" }
val kmpCoverageCheck by tasks.registering { group = "verification" }

val portableModules = mutableListOf<Map<String, String>>()
val writeKmpVerificationPlan by
    tasks.registering {
        val destination = layout.buildDirectory.file("kmp-evidence/modules.json")
        outputs.file(destination)
        outputs.upToDateWhen { false }
        doLast {
            check(portableModules.isNotEmpty()) { "No portable production modules selected" }
            destination.get().asFile.apply {
                parentFile.mkdirs()
                writeText(JsonOutput.toJson(portableModules))
            }
        }
    }

subprojects {
    pluginManager.withPlugin("faker-multiplatform-conventions") {
        val modulePath = path
        portableModules.add(
            mapOf("path" to path, "directory" to projectDir.relativeTo(rootDir).path)
        )
        kmpJvmCheck.configure { dependsOn("$modulePath:jvmTest", "$modulePath:jvmMaxJdkTest") }
        kmpJsCheck.configure { dependsOn("$modulePath:jsNodeTest", "$modulePath:jsBrowserTest") }
        kmpIosCheck.configure {
            dependsOn(
                "$modulePath:iosSimulatorArm64Test",
                "$modulePath:linkDebugTestIosArm64",
                "$modulePath:compileCommonMainKotlinMetadata",
            )
        }
        kmpCoverageCheck.configure { dependsOn("$modulePath:verifyCommonCoverage") }
    }
}

listOf(kmpJvmCheck, kmpJsCheck, kmpIosCheck, kmpCoverageCheck).forEach {
    it.configure { dependsOn(writeKmpVerificationPlan) }
}
