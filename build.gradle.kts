import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import io.github.serpro69.semverkt.gradle.plugin.tasks.TagTask
import utils.configureGradleDaemonJvm

plugins {
    alias(libs.plugins.nexus.publish)
    alias(libs.plugins.benmanes.versions)
}

group = "io.github.serpro69"

subprojects {
    apply {
        plugin("com.github.ben-manes.versions")
    }

    // don't apply the rest to bom subproject
    if (this@subprojects.name == "bom") return@subprojects

    tasks.withType<DependencyUpdatesTask> {
        // disable for cli-bot because the classpath takes forever to resolve
        enabled = this@subprojects.name != "cli-bot"
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

// Run :tag only after we've published artifacts to sonatype
tasks.withType<TagTask>().configureEach {
    // don't apply when "dryRun"
    findProperty("dryRun") ?: run {
        dependsOn("closeSonatypeStagingRepository")
    }
}

configureGradleDaemonJvm(
    project = project,
    updateDaemonJvm = tasks.updateDaemonJvm,
    gradleDaemonJvmVersion = libs.versions.gradleDaemonJvm.map { JavaVersion.toVersion(it) },
)
