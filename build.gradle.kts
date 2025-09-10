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

val ossrhUsername: String? by project
val ossrhPassword: String? by project

nmcpAggregation {
    centralPortal {
        username.set(ossrhUsername ?: System.getenv("NEW_MAVEN_CENTRAL_USERNAME"))
        password.set(ossrhPassword ?: System.getenv("NEW_MAVEN_CENTRAL_PASSWORD"))
        publishingType = "USER_MANAGED"
        publicationName = "Faker $version ${fakerSettings.enabledPublicationNamePrefixes.get()}"
    }
}

val publishToAppropriateCentralRepository by
    tasks.registering {
        group = "publishing"
        if (isRelease.get()) {
            dependsOn(tasks.named("publishAggregationToCentralPortal"))
        } else {
            dependsOn(tasks.named("publishAggregationToCentralPortalSnapshots"))
        }
    }

dependencies {
    nmcpAggregation(projects.kotlinFakerBom)
    nmcpAggregation(projects.kotlinFaker)

    // extension modules
    nmcpAggregation(projects.extension.kotlinFakerExtBlns)
    nmcpAggregation(projects.extension.kotlinFakerExtKotestProperty)

    // faker modules
    nmcpAggregation(projects.faker.kotlinFakerBooks)
    nmcpAggregation(projects.faker.kotlinFakerCommerce)
    nmcpAggregation(projects.faker.kotlinFakerCreatures)
    nmcpAggregation(projects.faker.kotlinFakerDatabases)
    nmcpAggregation(projects.faker.kotlinFakerEdu)
    nmcpAggregation(projects.faker.kotlinFakerGames)
    nmcpAggregation(projects.faker.kotlinFakerHumor)
    nmcpAggregation(projects.faker.kotlinFakerJapmedia)
    nmcpAggregation(projects.faker.kotlinFakerLorem)
    nmcpAggregation(projects.faker.kotlinFakerMisc)
    nmcpAggregation(projects.faker.kotlinFakerMovies)
    nmcpAggregation(projects.faker.kotlinFakerMusic)
    nmcpAggregation(projects.faker.kotlinFakerPictures)
    nmcpAggregation(projects.faker.kotlinFakerSports)
    nmcpAggregation(projects.faker.kotlinFakerTech)
    nmcpAggregation(projects.faker.kotlinFakerTravel)
    nmcpAggregation(projects.faker.kotlinFakerTvshows)
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
    findProperty("dryRun") ?: run { dependsOn("publishAggregationToCentralPortal") }
}

tasks.dokkaGfmMultiModule { moduleName.set("kotlin-faker") }
