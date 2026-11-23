import com.fasterxml.jackson.databind.ObjectMapper
import java.security.MessageDigest
import org.yaml.snakeyaml.Yaml

buildscript {
    repositories { mavenCentral() }
    dependencies {
        classpath("org.yaml:snakeyaml:2.2")
        classpath("com.fasterxml.jackson.core:jackson-databind:2.21.1")
    }
}

tasks.register("generatePayload") {
    val sourceDirectory = file("../../../core/src/jvmMain/resources/locales")
    val outputDirectory = layout.buildDirectory.dir("generated/payload")
    inputs.dir(sourceDirectory).withPathSensitivity(PathSensitivity.RELATIVE)
    inputs.file(file("payload.gradle.kts"))
    outputs.dir(outputDirectory)

    doLast {
        val started = System.nanoTime()
        val mapper = ObjectMapper()
        val output = outputDirectory.get().asFile
        output.deleteRecursively()
        val sourcesOutput = output.resolve("commonMain").also { it.mkdirs() }
        val files = sourceDirectory.walkTopDown()
            .filter { it.isFile && it.extension in setOf("yml", "yaml") }
            .sortedBy { it.relativeTo(sourceDirectory).invariantSeparatorsPath }.toList()
        check(files.isNotEmpty()) { "No dictionary inputs" }

        // Match production JSON conversion first, including Boolean map keys. Only then
        // canonicalize object order. Arrays and scalar kinds remain unchanged.
        fun canonical(value: Any?): Any? = when (value) {
            is Map<*, *> -> value.entries.associate { (k, v) ->
                check(k is String) { "Non-string JSON key: $k" }
                k to canonical(v)
            }.toSortedMap()
            is List<*> -> value.map(::canonical)
            null, is String, is Boolean, is Int, is Long -> value
            else -> error("Uninventoried scalar type: ${value.javaClass.name}")
        }

        fun literal(value: String): String = mapper.writeValueAsString(value).replace("$", "\\$")

        fun modifiedUtf8Size(value: String): Int = value.fold(0) { bytes, char ->
            bytes + when (char.code) { 0 -> 2; in 1..127 -> 1; in 128..2047 -> 2; else -> 3 }
        }

        fun chunks(value: String): List<String> {
            val result = mutableListOf<String>()
            var start = 0
            var offset = 0
            var bytes = 0
            while (offset < value.length) {
                val units = if (value[offset].isHighSurrogate() &&
                    offset + 1 < value.length && value[offset + 1].isLowSurrogate()) 2 else 1
                val nextBytes = modifiedUtf8Size(value.substring(offset, offset + units))
                if (offset - start + units > 8_000 || bytes + nextBytes > 24_000) {
                    result += value.substring(start, offset)
                    start = offset
                    bytes = 0
                }
                bytes += nextBytes
                offset += units
            }
            result += value.substring(start)
            check(result.joinToString("") == value)
            return result
        }

        val sourceEntries = mutableListOf<String>()
        val projectionEntries = mutableListOf<String>()
        val locales = sortedSetOf<String>()
        var jsonBytes = 0L
        var chunkCount = 0
        var largestChunkBytes = 0
        var largestChunkChars = 0

        fun emitProjection(sourceId: Int, category: String?, priority: Int, value: Any?) {
            val id = projectionEntries.size
            val json = mapper.writeValueAsString(canonical(value))
            val parts = chunks(json)
            jsonBytes += json.toByteArray(Charsets.UTF_8).size
            chunkCount += parts.size
            largestChunkBytes = maxOf(largestChunkBytes, parts.maxOf(::modifiedUtf8Size))
            largestChunkChars = maxOf(largestChunkChars, parts.maxOf { it.length })
            // Each projection has its own class/file and bounded literal-returning methods.
            // append calls prevent constant folding into one oversized JVM string constant.
            val code = buildString {
                appendLine("package probe.payload")
                appendLine("internal fun payload$id(): String = buildString(${json.length}) {")
                parts.indices.forEach { appendLine("    append(part${id}_$it())") }
                appendLine("}")
                parts.forEachIndexed { i, part ->
                    appendLine("private fun part${id}_$i(): String = ${literal(part)}")
                }
            }
            val generated = sourcesOutput.resolve("Payload$id.kt")
            generated.writeText(code)
            check(generated.readText() == code) { "UTF-8 encoding lost source characters: $id" }
            val categoryLiteral = category?.let(::literal) ?: "null"
            projectionEntries += "PayloadProjection($sourceId, $categoryLiteral, $priority, ${parts.size}, ::payload$id)"
        }

        files.forEachIndexed { id, file ->
            val path = file.relativeTo(sourceDirectory).invariantSeparatorsPath
            val locale = path.substringBefore('/').removeSuffix(".yml").removeSuffix(".yaml").replace('_', '-')
            val resource = if ('/' in path) file.nameWithoutExtension else null
            locales += locale
            val yaml = file.inputStream().use { Yaml().loadAs(it, Map::class.java) }
            val converted = mapper.readValue(mapper.writeValueAsString(yaml), Map::class.java)
            check(converted.size == 1) { "Expected one locale root: $path" }
            val rootKey = converted.keys.single() as String
            val root = converted.values.single() as Map<*, *>
            val faker = root["faker"] as Map<*, *>
            sourceEntries += "PayloadSource(${literal(path)}, ${literal(locale)}, ${literal(rootKey)}, ${resource?.let(::literal) ?: "null"})"
            val metadata = root.filterKeys { it != "faker" }
            if (metadata.isNotEmpty()) emitProjection(id, null, 0, metadata)
            faker.entries.sortedBy { it.key as String }.forEach { (key, value) ->
                val category = key as String
                val priority = when (resource) { null -> 0; category -> 1; else -> 2 }
                emitProjection(id, category, priority, value)
            }
        }

        fun emitIndex(name: String, type: String, entries: List<String>) {
            val groups = entries.chunked(32)
            sourcesOutput.resolve("$name.kt").writeText(buildString {
                appendLine("package probe.payload")
                appendLine("internal fun $name(): List<$type> = buildList {")
                groups.indices.forEach { appendLine("    addAll(${name}Part$it())") }
                appendLine("}")
            })
            groups.forEachIndexed { i, group ->
                sourcesOutput.resolve("${name}Part$i.kt").writeText(buildString {
                    appendLine("package probe.payload")
                    appendLine("internal fun ${name}Part$i(): List<$type> = listOf(")
                    group.forEach { appendLine("    $it,") }
                    appendLine(")")
                })
            }
        }
        emitIndex("generatedSources", "PayloadSource", sourceEntries)
        emitIndex("generatedProjections", "PayloadProjection", projectionEntries)
        sourcesOutput.resolve("LocaleLayers.kt").writeText(buildString {
            appendLine("package probe.payload")
            appendLine("internal val localeLayers: Map<String, List<String>> = mapOf(")
            locales.forEach { locale ->
                val layers = listOf("en", locale.substringBefore('-'), locale).distinct()
                appendLine("    ${literal(locale)} to listOf(${layers.joinToString(", ", transform = ::literal)}),")
            }
            appendLine(")")
        })

        val generatedFiles = sourcesOutput.listFiles()!!.sortedBy { it.name }
        val digest = MessageDigest.getInstance("SHA-256")
        generatedFiles.forEach {
            digest.update(it.name.toByteArray(Charsets.UTF_8))
            digest.update(0.toByte())
            digest.update(it.readBytes())
        }
        val report = sortedMapOf(
            "sources" to files.size,
            "locales" to locales.size,
            "projections" to projectionEntries.size,
            "chunks" to chunkCount,
            "maxChunkModifiedUtf8Bytes" to largestChunkBytes,
            "maxChunkChars" to largestChunkChars,
            "payloadJsonUtf8Bytes" to jsonBytes,
            "generatedSourceBytes" to generatedFiles.sumOf { it.length() },
            "generatedSourceSha256" to digest.digest().joinToString("") { "%02x".format(it) },
        )
        output.resolve("manifest.json").writeText(mapper.writeValueAsString(report) + "\n")
        println("Payload generation: ${mapper.writeValueAsString(report)}")
        println("Payload generation elapsedMs=${(System.nanoTime() - started) / 1_000_000}")
    }
}

tasks.register("measurePayloadArtifacts") {
    doLast {
        val directory = layout.buildDirectory.get().asFile
        val sizes = sortedMapOf<String, Long>()
        // Measure actual outputs, excluding incremental compiler caches and stale cache versions.
        listOf("libs", "bin", "compileSync/js/test/testDevelopmentExecutable/kotlin").forEach { path ->
            directory.resolve(path).walkTopDown()
                .filter { it.isFile && it.extension in setOf("jar", "klib", "kexe", "js", "mjs") }
                .forEach { sizes[it.relativeTo(directory).invariantSeparatorsPath] = it.length() }
        }
        // KGP emits unpacked klibs for main compilations; these are directories, not .klib files.
        directory.resolve("classes/kotlin").listFiles()?.filter { it.isDirectory && it.name != "jvm" }
            ?.forEach { target ->
                val main = target.resolve("main")
                if (main.isDirectory) {
                    sizes[main.relativeTo(directory).invariantSeparatorsPath + "/"] =
                        main.walkTopDown().filter { it.isFile }.sumOf { it.length() }
                }
            }
        check(sizes.isNotEmpty()) { "Build target artifacts before measuring them" }
        val report = directory.resolve("payload-artifacts.tsv")
        report.writeText("path\tbytes\n" + sizes.entries.joinToString("\n", postfix = "\n") {
            "${it.key}\t${it.value}"
        })
        println(report.readText())
    }
}
