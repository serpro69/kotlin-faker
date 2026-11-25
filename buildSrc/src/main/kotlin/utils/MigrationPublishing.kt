package utils

import io.github.serpro69.semverkt.gradle.plugin.tasks.TagTask
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.publish.maven.tasks.PublishToMavenRepository

/** Local staging is allowed during migration, including development and snapshot versions. */
fun PublishToMavenRepository.isProjectLocalStaging(): Boolean {
    val url = repository.url
    return url.scheme == "file" &&
        url.authority.isNullOrEmpty() &&
        java.io.File(url).canonicalFile.toPath().startsWith(project.rootDir.canonicalFile.toPath())
}

private fun Task.isMigrationReleaseTask(): Boolean =
    this is TagTask ||
        name in setOf("publishToAppropriateCentralRepository", "release") ||
        generateSequence(javaClass as Class<*>?) { it.superclass }
            .any {
                // NMCP 1.0.2's upload types, including renamed tasks and aggregate upload tasks.
                it.name in
                    setOf(
                        "nmcp.internal.task.NmcpPublishWithPublisherApiTask",
                        "nmcp.internal.task.NmcpPublishFileByFileToSnapshotsTask",
                    )
            }

/**
 * Closed through Task 24. Reopening requires a separately authorized release change, not a flag.
 * Check the complete graph before any action: onlyIf/disabled uploads must not permit tagging.
 */
fun Project.installMigrationPublicationGuard() {
    check(this == rootProject)
    allprojects {
        tasks.configureEach {
            if (this is PublishToMavenRepository || isMigrationReleaseTask()) {
                notCompatibleWithConfigurationCache(
                    "Migration publication guard checks the task graph"
                )
            }
        }
    }
    gradle.taskGraph.whenReady {
        val forbidden =
            allTasks.filter {
                (it is PublishToMavenRepository && !it.isProjectLocalStaging()) ||
                    it.isMigrationReleaseTask()
            }
        if (forbidden.isNotEmpty()) {
            throw GradleException(
                "KMP migration blocks remote publication and release/tag tasks through Task 24 " +
                    "and until a separate release is authorized: " +
                    forbidden.joinToString { it.path } +
                    ". Use the project-local RootBuildDir repository for staging."
            )
        }
    }
}
