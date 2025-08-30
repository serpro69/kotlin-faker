import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import io.github.serpro69.semverkt.gradle.plugin.tasks.TagTask
import utils.configureGradleDaemonJvm

plugins {
    id("faker-base")
    id("com.gradleup.nmcp.aggregation")
    alias(libs.plugins.bcv)
    alias(libs.plugins.nexus.publish)
    alias(libs.plugins.benmanes.versions)
    alias(libs.plugins.dokka)
}

group = "io.github.serpro69"

apiValidation { ignoredProjects.addAll(listOf("cli-bot", "test")) }

nmcpAggregation {
    centralPortal {
        username.set(System.getenv("NEW_MAVEN_CENTRAL_USERNAME"))
        password.set(System.getenv("NEW_MAVEN_CENTRAL_PASSWORD"))
        publishingType = "USER_MANAGED"
        publicationName =
            "Faker ${Ci.publishVersion} ${fakerSettings.enabledPublicationNamePrefixes.get()}"
    }
}

val publishToAppropriateCentralRepository by
    tasks.registering {
        group = "publishing"
        if (Ci.isRelease) {
            dependsOn(tasks.named("publishAggregationToCentralPortal"))
        } else {
            dependsOn(tasks.named("publishAggregationToCentralPortalSnapshots"))
        }
    }

dependencies {
    nmcpAggregation(projects.bom)
    nmcpAggregation(projects.core)

    // extension modules
    nmcpAggregation(projects.extension.blns)
    nmcpAggregation(projects.extension.kotestProperty)

    // faker modules
    nmcpAggregation(projects.faker.books)
    nmcpAggregation(projects.faker.commerce)
    nmcpAggregation(projects.faker.creatures)
    nmcpAggregation(projects.faker.databases)
    nmcpAggregation(projects.faker.edu)
    nmcpAggregation(projects.faker.games)
    nmcpAggregation(projects.faker.humor)
    nmcpAggregation(projects.faker.japmedia)
    nmcpAggregation(projects.faker.lorem)
    nmcpAggregation(projects.faker.misc)
    nmcpAggregation(projects.faker.movies)
    nmcpAggregation(projects.faker.music)
    nmcpAggregation(projects.faker.pictures)
    nmcpAggregation(projects.faker.sports)
    nmcpAggregation(projects.faker.tech)
    nmcpAggregation(projects.faker.travel)
    nmcpAggregation(projects.faker.tvshows)
}

configureGradleDaemonJvm(
    project = project,
    updateDaemonJvm = tasks.updateDaemonJvm,
    gradleDaemonJvmVersion = libs.versions.gradleDaemonJvm,
)

subprojects {
    apply { plugin("com.github.ben-manes.versions") }

    // don't apply the rest to bom subproject
    if (this@subprojects.name == "bom") return@subprojects

    tasks.withType<DependencyUpdatesTask> {
        // disable for cli-bot because the classpath takes forever to resolve
        enabled = this@subprojects.name != "cli-bot"
        fun isNonStable(version: String): Boolean {
            val stableKeyword =
                listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
            val regex = "^[0-9,.v-]+(-r|-jre)?$".toRegex()
            val isStable = stableKeyword || regex.matches(version)
            return isStable.not()
        }

        rejectVersionIf { isNonStable(candidate.version) }
    }
}

// nexusPublishing {
//     repositories {
//         // see
// https://central.sonatype.org/publish/publish-portal-ossrh-staging-api/#configuration
//         sonatype {
//             nexusUrl.set(uri("https://ossrh-staging-api.central.sonatype.com/service/local/"))
//             snapshotRepositoryUrl.set(
//                 uri("https://central.sonatype.com/repository/maven-snapshots/")
//             )
//         }
//     }
// }

// Run :tag only after we've published artifacts to sonatype
tasks.withType<TagTask>().configureEach {
    // don't apply when "dryRun"
    findProperty("dryRun") ?: run { dependsOn("closeSonatypeStagingRepository") }
}

tasks.dokkaGfmMultiModule { moduleName.set("kotlin-faker") }
