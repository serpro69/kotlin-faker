import io.github.serpro69.semverkt.gradle.plugin.tasks.TagTask

/**
 * Plugin for publishing conventions
 */

plugins {
    `maven-publish`
    signing
    id("faker-base-conventions")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = fullName
            version = project.version.toString()
            when {
                isBomModule -> from(components["javaPlatform"])
                else -> from(components["java"])
            }
            if (!isBomModule) {
                artifact(sourcesJar)
                artifact(dokkaJavadocJar) //TODO: configure dokka or use defaults?
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
                        name.set("Særgeir")
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
