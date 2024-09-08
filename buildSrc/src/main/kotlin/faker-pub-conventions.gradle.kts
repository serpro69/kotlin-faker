import io.github.serpro69.semverkt.gradle.plugin.tasks.TagTask

/**
 * Plugin for publishing conventions
 */

plugins {
    `maven-publish`
    signing
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = fullName
            version = project.version.toString()
            when {
                isBomModule -> from(components["javaPlatform"])
                !isShadow -> from(components["java"])
                else -> {
                    // TODO figure out how to include shadowed component here
                    //  See also faker-lib-conventions.gradle.kts publishing configuration
                }
            }
            if (!isBomModule) {
                artifact(sourcesJar)
                artifact(dokkaJavadocJar) //TODO configure dokka or use defaults?
            }

            pom {
                packaging = if (isBomModule) "pom" else "jar"
                name.set(if (isBomModule) "kotlin-faker" else fullName)
                description.set("Generate realistically looking fake data such as names, addresses, banking details, and many more, that can be used for testing and data anonymization purposes.")
                url.set("https://github.com/serpro69/kotlin-faker")
                scm {
                    connection.set("scm:git:https://github.com/serpro69/kotlin-faker")
                    developerConnection.set("scm:git:https://github.com/serpro69")
                    url.set("https://github.com/serpro69/kotlin-faker")
                }
                issueManagement {
                    url.set("https://github.com/serpro69/kotlin-faker/issues")
                }
                licenses {
                    license {
                        name.set("MIT")
                        url.set("https://opensource.org/licenses/mit-license.php")
                    }
                }
                developers {
                    developer {
                        id.set("serpro69")
                        name.set("Serhii Prodan")
                    }
                }
            }
        }
    }
}

if (!isBomModule) {
    artifacts {
        archives(sourcesJar)
        archives(dokkaJavadocJar)
    }
}

signing {
    sign(publishing.publications["maven"])
}

tasks.withType<PublishToMavenRepository>().configureEach {
    dependsOn(project.tasks.getByName("tag")) // needed for onlyIf conditions
    dependsOn(project.tasks.withType(Sign::class.java))
    if (isShadow) dependsOn(project.tasks["shadowJar"])
    onlyIf { !isDev.get() }
    onlyIf { isRelease.get() || isSnapshot.get() }
}

tasks.withType<PublishToMavenLocal>().configureEach {
    onlyIf { isDev.get() || isSnapshot.get() }
}

tasks.withType<Sign>().configureEach {
    dependsOn(project.tasks.getByName("tag")) // needed for onlyIf conditions
    onlyIf { !isDev.get() && !isSnapshot.get() }
    onlyIf { isRelease.get() }
}

//// Run :tag only after we've published artifacts to sonatype
//tasks.withType<TagTask>().configureEach {
//    // don't apply when "dryRun"
//    findProperty("dryRun") ?: run {
//        dependsOn(rootProject.tasks.getByName("closeSonatypeStagingRepository"))
//    }
//}
