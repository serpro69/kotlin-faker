import io.github.serpro69.semverkt.gradle.plugin.tasks.TagTask
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType.common
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType.jvm

/** Plugin for publishing conventions */
plugins {
    id("faker-base")
    signing
    `maven-publish`
    id("dev.adamko.dev-publish")
    id("com.gradleup.nmcp")
}

val signingKey: String? by project
val signingPassword: String? by project

val mavenCentralRepoName = "Deploy"

signing {
    if (!signingKey.isNullOrBlank() && !signingPassword.isNullOrBlank()) {
        useGpgCmd()
        useInMemoryPgpKeys(signingKey, signingPassword)
    } // else use default signing plugin configuration
    sign(publishing.publications)
    setRequired { isRelease.get() } // only require signing when releasing
}

// region Only enabling signing when publishing to Maven Central.
// (Otherwise signing is required for dev-publish, which prevents testing if the credentials aren't
// present.)
gradle.taskGraph.whenReady {
    val isPublishingToMavenCentral = isRelease.get()
    if (isPublishingToMavenCentral) {
        logger.lifecycle("[faker-publishing] Publishing ${project.name}:${version} to Maven Central, signing is required")
    } else {
        logger.lifecycle(
            "[faker-publishing] Not publishing ${project.name}:${version} to Maven Central, signing is not required"
        )
    }

    signing.setRequired({ isPublishingToMavenCentral })

    tasks.withType<Sign>().configureEach {
        // redefine val for Config Cache compatibility
        @Suppress("LocalVariableName")
        val isPublishingToMavenCentral_ = isPublishingToMavenCentral
        inputs.property("isPublishingToMavenCentral", isPublishingToMavenCentral_)
        onlyIf("neither dev, nor snapshot") { !isDev.get() && !isSnapshot.get() }
        onlyIf("publishing to Maven Central") { isPublishingToMavenCentral_ }
    }
}

// endregion

publishing {
    repositories {
        maven(rootDir.resolve("build/maven-repo")) {
            // Publish to a project-local directory, for easier verification of published artifacts
            // Run ./gradlew publishAllPublicationsToRootBuildDirRepository, and check
            // `$rootDir/build/maven-repo/`
            name = "RootBuildDir"
        }
    }

    publications.withType<MavenPublication>().configureEach {
        pom {
            name.set("kotlin-faker")
            description.set(
                "Generate realistically looking fake data such as names, addresses, banking details, and many more, that can be used for testing and data anonymization purposes."
            )
            url.set("https://github.com/serpro69/kotlin-faker")

            scm {
                connection.set("scm:git:https://github.com/serpro69/kotlin-faker")
                developerConnection.set("scm:git:https://github.com/serpro69")
                url.set("https://github.com/serpro69/kotlin-faker")
            }

            issueManagement { url.set("https://github.com/serpro69/kotlin-faker/issues") }

            licenses {
                license {
                    name.set("MIT")
                    url.set("https://opensource.org/licenses/mit-license.php")
                }
            }

            developers {
                developer {
                    id.set("serpro69")
                    name.set("Særgeir")
                }
            }
        }
    }
}

pluginManager.withPlugin("org.jetbrains.kotlin.multiplatform") {
    val javadocJar by
        tasks.registering(Jar::class) {
            group = JavaBasePlugin.DOCUMENTATION_GROUP
            description = "Create Javadoc JAR"
            archiveClassifier.set("javadoc")
        }

    publishing.publications.withType<MavenPublication>().configureEach { artifact(javadocJar) }

    publishPlatformArtifactsInRootModule(project)
}

pluginManager.withPlugin("java-gradle-plugin") {
    extensions.configure<JavaPluginExtension> { withSourcesJar() }
}

// region Maven Central can't handle parallel uploads, so limit parallel uploads with a BuildService

abstract class MavenPublishLimiter : BuildService<BuildServiceParameters.None>

val mavenPublishLimiter =
    gradle.sharedServices.registerIfAbsent("mavenPublishLimiter", MavenPublishLimiter::class) {
        maxParallelUsages = 1
    }

tasks
    .withType<PublishToMavenRepository>()
    .matching { it.name.endsWith("PublicationTo${mavenCentralRepoName}Repository") }
    .configureEach { usesService(mavenPublishLimiter) }

// endregion

// region FakerBomService

/**
 * Create a service for collecting the coordinates of all faker artifacts that should be included in
 * the bom.
 */
abstract class FakerBomService : BuildService<BuildServiceParameters.None> {
    /** Coordinates that will be included in the faker BOM. */
    abstract val coordinates: SetProperty<String>
}

val fakerBomService: FakerBomService =
    gradle.sharedServices.registerIfAbsent("fakerBomService", FakerBomService::class).get()

extensions.add("fakerBomService", fakerBomService)

/** Controls whether the current subproject will be included in the faker-bom. */
val includeInFakerBom: Property<Boolean> =
    objects.property<Boolean>().convention(project.name != "kotlin-faker-bom")

extensions.add<Property<Boolean>>("includeInFakerBom", includeInFakerBom)

pluginManager.withPlugin("org.jetbrains.kotlin.multiplatform") {
    extensions.configure<KotlinMultiplatformExtension> {
        targets
            .matching { target ->
                target.publishable &&
                    // Skip platform artifacts (like *-linuxx64, *-macosx64)
                    // It leads to inconsistent bom when publishing from different platforms
                    // (e.g. on linux it will include only linuxx64 artifacts and no macosx64)
                    // It shouldn't be a problem as usually consumers need to use generic *-native
                    // artifact
                    // Gradle will choose correct variant by using metadata attributes
                    (target.platformType == common || target.platformType == jvm)
            }
            .all {
                mavenPublication publication@{
                    fakerBomService.coordinates.addAll(
                        providers
                            .provider {
                                // We are publishing the -jvm jars in the root variants so we can
                                // simply remove the -jvm suffix
                                // See: publishPlatformArtifactsInRootModule
                                "${this@publication.groupId}:${this@publication.artifactId}:${this@publication.version}"
                            }
                            .zip(includeInFakerBom) { coords, enabled ->
                                if (enabled) listOf(coords, coords.replace("-jvm", ""))
                                else emptyList()
                            }
                    )
                }
            }
    }
}

// region Letting Faker settings control which publications are enabled
tasks.withType<AbstractPublishToMaven>().configureEach {
    onlyIf {
        val enabled = isPublicationEnabled(publication.name).get()
        logger.lifecycle("[task: $path] publishing for ${publication.name} is disabled")
        enabled
    }
}

private val fakerSettings = extensions.getByType<BuildSettings>()

private fun isPublicationEnabled(publicationName: String): Provider<Boolean?> {
    return publicationName.let { name ->
        fakerSettings.enabledPublicationNamePrefixes.map { prefixes ->
            prefixes.any { prefix -> name.startsWith(prefix, ignoreCase = true) }
        }
    }
}

// endregion

// region Fix Gradle error Reason: Task <publish> uses this output of task <sign> without declaring
// an explicit or implicit dependency.
// https://github.com/gradle/gradle/issues/26091
tasks.withType<AbstractPublishToMaven>().configureEach {
    val signingTasks = tasks.withType<Sign>()
    mustRunAfter(signingTasks)
}

// endregion

tasks.withType<PublishToMavenRepository>().configureEach {
    dependsOn(project.tasks.withType(Sign::class.java))
    onlyIf { !isDev.get() }
    onlyIf { isRelease.get() || isSnapshot.get() }
}

tasks.withType<PublishToMavenLocal>().configureEach { onlyIf { isDev.get() || isSnapshot.get() } }

tasks.withType<TagTask>().configureEach {
    usesService(mavenPublishLimiter)
    // even with above, race-conditions seem to happen sometimes,
    // hence depend on root :tag task to avoid failures of :<module>:tag tasks
    dependsOn(":tag")
}
