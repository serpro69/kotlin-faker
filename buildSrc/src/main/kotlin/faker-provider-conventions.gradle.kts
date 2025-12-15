/** Plugin for :faker:* modules */
plugins { id("faker-lib-conventions") }

val core =
    rootProject.subprojects.first { it.path == ":kotlin-faker" }
        ?: throw GradleException(":kotlin-faker project not found")

kotlin {
    sourceSets {
        val jvmMain by getting {
            dependencies {
                /* :core is versioned separately,
                during development versions will always equal
                  (both are set to a version placeholder via gradle.properties),
                but during publishing they might not (depending on changes to a given module)
                hence we first check the versions equality, and then
                EITHER set a dependency on a published :core artifact with latest version
                  (latest is handled by semver.kt plugin for multi-tag monorepos),
                OR a project-type dependency on the :core submodule */
                // In order to use an additional fake data provider, core faker needs to be on the
                // classpath.
                // Don't add it as transitive dependency to each faker provider
                compileOnly(project(path = core.path))
            }
        }
        val jvmTest by getting {
            dependencies {
                // we need implementation dependency for tests to be able to access 'core'
                // functionality
                implementation(project(path = core.path))
                // provides helpers for integration tests
                implementation(project(":test", "testHelper"))
            }
        }
    }
}
