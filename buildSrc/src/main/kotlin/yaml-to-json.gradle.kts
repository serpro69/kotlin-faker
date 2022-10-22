import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import org.gradle.kotlin.dsl.create
import kotlin.system.exitProcess

interface Yaml2JsonPluginExtension {
    val input: Property<File>
    val output: Property<File>
}

class Yaml2JsonPlugin : Plugin<Project> {
    val jsonMapper = ObjectMapper()
    val yamlMapper = ObjectMapper(YAMLFactory())

    override fun apply(p: Project) {
        val ext = p.extensions.create<Yaml2JsonPluginExtension>("yaml2jsonExt")
        p.tasks.register("yaml2json") {
            val input = ext.input.get().absoluteFile
            val output = ext.output.get().absoluteFile

            doFirst {
                if (!input.isDirectory) throw IllegalArgumentException("$input is not a directory or does not exist")
            }

            doLast {
                val map = yamlMapper.readValue(input.inputStream(), Map::class.java)
                jsonMapper.writeValue(output, map)
            }
        }
    }
}

