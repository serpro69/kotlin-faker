import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import org.gradle.kotlin.dsl.create

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
                if (output.exists() && !output.isDirectory) throw IllegalArgumentException("$output is not a directory")
            }

            doLast {
                output.deleteRecursively()
                if (input.isDirectory) {
                    input.getYmlFiles().forEach { src ->
                        val outFile = src.relativeTo(input.parentFile)
                        val dest = output
                            .resolve(File("${(outFile.parentFile.resolve(outFile.nameWithoutExtension))}.json"))
                            .also {
                                it.parentFile.mkdirs()
                                it.createNewFile()
                            }
                        writeYamlToJson(src, dest)
                    }
                } else if (input.extension == "yml" || input.extension == "yaml") {
                    val dest = output.resolve("${input.nameWithoutExtension}.json").also {
                        it.parentFile.mkdirs()
                        it.createNewFile()
                    }
                    writeYamlToJson(input, dest)
                } else throw IllegalArgumentException("Unable to process file $input")
            }
        }
    }

    private fun File.getYmlFiles(): Sequence<File> {
        return walk().filter { f -> f.isFile && (f.extension == "yml" || f.extension == "yaml") }
    }

    private fun writeYamlToJson(src: File, dest: File) {
        val map = yamlMapper.readValue(src.inputStream(), Map::class.java)
        jsonMapper.writeValue(dest, map)
    }
}

