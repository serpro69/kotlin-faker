package probe

import com.fasterxml.jackson.databind.ObjectMapper
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import org.yaml.snakeyaml.Yaml
import probe.payload.PayloadStore
import probe.payload.payloadProjections
import probe.payload.payloadSources

class PayloadSourceTest {
    @Test
    fun everyCompiledSourceReconstructsTheOriginalProductionConversion() {
        val directory = File(requireNotNull(System.getProperty("payload.sourceRoot")))
        val files = directory.walkTopDown()
            .filter { it.isFile && it.extension in setOf("yml", "yaml") }.toList()
        assertEquals(files.map { it.relativeTo(directory).invariantSeparatorsPath }.sorted(),
            payloadSources.map { it.path }.sorted())
        val mapper = ObjectMapper()
        for (file in files) {
            val path = file.relativeTo(directory).invariantSeparatorsPath
            val id = payloadSources.indexOfFirst { it.path == path }
            val source = payloadSources[id]
            // Independent oracle: the original YAML loader + production JSON conversion,
            // not the generator's canonicalization or partitioning functions.
            val original = file.inputStream().use { Yaml().loadAs(it, Map::class.java) }
            val expected = Json.parseToJsonElement(mapper.writeValueAsString(original))
            val store = PayloadStore()
            val entries = payloadProjections.filter { it.sourceId == id }
            val metadata = entries.singleOrNull { it.category == null }
                ?.let { store.projection(path, null).jsonObject } ?: JsonObject(emptyMap())
            val categories = entries.filter { it.category != null }.associate {
                requireNotNull(it.category) to store.projection(path, it.category)
            }
            val root = JsonObject(metadata + ("faker" to JsonObject(categories)))
            assertEquals(expected, JsonObject(mapOf(source.parsedRootKey to root)), path)
        }
    }
}
