import io.github.serpro69.semverkt.gradle.plugin.tasks.TagTask

plugins {
    `java-platform`
    `faker-pub-conventions`
}

val bom = project

// Exclude subprojects that will never be published so that when configuring this project
// we don't force their configuration and do unnecessary work
val excludeFromBom =
    listOf(
        ":cli-bot",
        ":extension",
        ":faker",
        ":test",
        ":extension:kotest-property-test",
    )

fun projectsFilter(candidateProject: Project) =
    excludeFromBom.none { candidateProject.path == it }
        && candidateProject.tasks.findByName("publish")?.enabled ?: false
        && candidateProject.path != bom.path

// Declare that this subproject depends on all subprojects matching the filter
// When this subproject is configured, it will force configuration of all subprojects
// so that we can declare dependencies on them
rootProject.subprojects.filter(::projectsFilter).forEach { bom.evaluationDependsOn(it.path) }

dependencies {
    constraints {
        val includeInBom =
        rootProject.subprojects
            .filter { project ->
                logger.info(
                    "Include {} in bom: {}",
                    project,
                    projectsFilter(project),
                )
                // Only declare dependencies on projects that will have publications
                projectsFilter(project) && project.tasks.findByName("publish")?.enabled == true
            }
            .forEach { project ->
                project.publishing.publications.forEach { publication: Publication ->
                    if (publication is MavenPublication) {
                        // use publication coordinates rather than project because they could differ
                        api(
                            "${publication.groupId}:${publication.artifactId}:${publication.version}"
                        )
                    }
                }
            }
    }
}

publishing {
    publications {
        create<MavenPublication>("KotlinFakerBom") {
            from(components["javaPlatform"])
        }
    }
}
