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

configurations.api.configure {
    // lazily add the coords from all subprojects to the kotest-bom
    dependencyConstraints.addAllLater(
        fakerBomService.coordinates.map { coords ->
            logger.lifecycle("[$path] adding ${coords.size} coords to kotest-bom: $coords")
            coords
                .distinct()
                .sorted()
                .map { coord ->
                    logger.lifecycle("[$path] adding $coord dependencies")
                    project.dependencies.constraints.create(coord)
                }
        }
    )
}

publishing {
    publications {
        create<MavenPublication>("KotlinFakerBom") {
            from(components["javaPlatform"])
        }
    }
}
