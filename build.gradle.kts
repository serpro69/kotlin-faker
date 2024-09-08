import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import utils.configureGradleDaemonJvm

plugins {
    alias(libs.plugins.nexus.publish)
    alias(libs.plugins.benmanes.versions)
    alias(libs.plugins.kotlin.bcv)
}

group = "io.github.serpro69"

subprojects {
    group = rootProject.group

    apply {
        plugin("com.github.ben-manes.versions")
    }

    // don't apply the rest to bom subproject
    if (this@subprojects.name == "bom") return@subprojects

    tasks.withType<DependencyUpdatesTask> {
        fun isNonStable(version: String): Boolean {
            val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
            val regex = "^[0-9,.v-]+(-r|-jre)?$".toRegex()
            val isStable = stableKeyword || regex.matches(version)
            return isStable.not()
        }

        rejectVersionIf {
            isNonStable(candidate.version)
        }
    }
}

nexusPublishing {
    repositories {
        sonatype {
            stagingProfileId.set(properties["stagingProfileId"]?.toString())
        }
    }
}

apiValidation {
    ignoredProjects += listOf("bom", "cli-bot", /*"docs",*/ "test")
}

configureGradleDaemonJvm(
    project = project,
    updateDaemonJvm = tasks.updateDaemonJvm,
    gradleDaemonJvmVersion = libs.versions.gradleDaemonJvm.map { JavaVersion.toVersion(it) },
)
