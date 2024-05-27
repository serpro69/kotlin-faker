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

signing {
    sign(publishing.publications["maven"])
}

tasks.withType<PublishToMavenRepository>().configureEach {
    dependsOn(project.tasks.getByName("tag"))
    dependsOn(project.tasks.withType(Sign::class.java))
    if (isShadow) dependsOn(project.tasks["shadowJar"])
    onlyIf("Not dev") { !isDev.get() }
    onlyIf("Release or snapshot") { isRelease.get() || isSnapshot.get() }
}

tasks.withType<PublishToMavenLocal>().configureEach {
    onlyIf("In development") { isDev.get() || isSnapshot.get() }
}

tasks.withType<Sign>().configureEach {
    dependsOn(project.tasks.getByName("tag"))
    onlyIf("Not dev and snapshot") { !isDev.get() && !isSnapshot.get() }
    onlyIf("Release") { isRelease.get() }
}
