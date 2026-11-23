package probe.payload

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement

internal data class PayloadSource(
    val path: String,
    val locale: String,
    val parsedRootKey: String,
    val resource: String?,
)

internal data class PayloadProjection(
    val sourceId: Int,
    val category: String?, // null retains non-faker locale metadata, such as en.time
    val sourceKindRank: Int, // locale-wide (0), primary file (1), secondary file (2)
    val chunks: Int,
    val json: () -> String,
)

internal val payloadSources: List<PayloadSource> by lazy { generatedSources() }
internal val payloadProjections: List<PayloadProjection> by lazy { generatedProjections() }

/** Decodes one physical source/category projection. It performs no fallback or expressions. */
internal class PayloadStore {
    private val decoded = mutableMapOf<Pair<String, String?>, JsonElement>()

    val decodedCount: Int
        get() = decoded.size

    fun projection(sourcePath: String, category: String?): JsonElement {
        val key = sourcePath to category
        return decoded.getOrPut(key) {
            val entry = payloadProjections.singleOrNull {
                payloadSources[it.sourceId].path == sourcePath && it.category == category
            } ?: error("Missing payload projection: $sourcePath / $category")
            Json.parseToJsonElement(entry.json())
        }
    }
}
