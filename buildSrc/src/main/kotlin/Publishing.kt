import gradle.kotlin.dsl.accessors._617ff5292df7551646490c1442241820.dokkaJavadoc
import gradle.kotlin.dsl.accessors._617ff5292df7551646490c1442241820.sourceSets
import org.gradle.api.Project
import org.gradle.api.tasks.bundling.Jar
import org.gradle.configurationcache.extensions.capitalized
import org.gradle.kotlin.dsl.creating
import org.gradle.kotlin.dsl.getValue

/**
 * For additional providers, use a combination of rootProject and subproject names for artifact name and similar things.
 * i.e. kotlin-faker-books, kotlin-faker-movies, kotlin-faker-tv, ...
 *
 * The "core" lib retains the same name as before: kotlin-faker
 */
val Project.fullName: String
    get() = if (name == "core") rootProject.name
    else "${rootProject.name}-${name}"

private fun createSourcesJarTask(p: Project): Jar {
    val sourcesJar by p.tasks.creating(Jar::class) {
        archiveClassifier.set("sources")
        from(p.sourceSets.getByName("main").allSource)
        from("${p.rootProject.rootDir.resolve("LICENSE.adoc")}") {
            into("META-INF")
        }
    }
    return sourcesJar
}

private fun createJavadocJarTask(p: Project): Jar {
    val dokkaJavadocJar by p.tasks.creating(Jar::class) {
        archiveClassifier.set("javadoc")
        dependsOn(p.tasks.dokkaJavadoc)
        from(p.tasks.dokkaJavadoc.get().outputDirectory.orNull)
    }
    return dokkaJavadocJar
}

val Project.sourcesJar: Jar
    get() {
        val sourcesJar = tasks.findByName("sourcesJar")
        if (sourcesJar != null && sourcesJar is Jar) {
            return sourcesJar
        }
        return createSourcesJarTask(this)
    }

val Project.dokkaJavadocJar: Jar
    get() {
        val dokkaJavadocJar = tasks.findByName("dokkaJavadocJar")
        if (dokkaJavadocJar != null && dokkaJavadocJar is Jar) {
            return dokkaJavadocJar
        }
        return createJavadocJarTask(this)
    }

