package probe

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertSame
import kotlin.test.assertTrue
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import probe.payload.PayloadStore
import probe.payload.localeLayers
import probe.payload.payloadProjections
import probe.payload.payloadSources

class PayloadTest {
    @Test
    fun everyProjectionRoundTripsOnThisTarget() {
        assertEquals(326, payloadSources.size)
        assertEquals(59, localeLayers.size)
        val keys = payloadProjections.map { payloadSources[it.sourceId].path to it.category }
        assertEquals(keys.size, keys.toSet().size)
        assertEquals(payloadSources.indices.toSet(), payloadProjections.map { it.sourceId }.toSet())
        for (entry in payloadProjections) {
            val source = payloadSources[entry.sourceId]
            val store = PayloadStore()
            val decoded = store.projection(source.path, entry.category)
            assertEquals(entry.json(), decoded.toString(), "${source.path} / ${entry.category}")
            assertCanonicalKeys(decoded)
            assertEquals(1, store.decodedCount)
        }
        println("Payload checked: ${payloadSources.size} sources, ${localeLayers.size} locales, ${keys.size} projections")
    }

    @Test
    fun categoryAccessDecodesOnlyTheRequestedProjection() {
        val store = PayloadStore()
        assertEquals(0, store.decodedCount)
        val name = store.projection("de.yml", "name")
        assertTrue(name.jsonObject.containsKey("first_name"))
        assertEquals(1, store.decodedCount)
        assertSame(name, store.projection("de.yml", "name"))
        assertEquals(1, store.decodedCount)
        store.projection("de.yml", "address")
        assertEquals(2, store.decodedCount)
        store.projection("en/name.yml", "name")
        assertEquals(3, store.decodedCount)
        assertFailsWith<IllegalStateException> { store.projection("missing.yml", "name") }
        assertEquals(3, store.decodedCount)
    }

    @Test
    fun aliasesAndExistingBooleanCoercionsArePreserved() {
        val store = PayloadStore()
        val name = store.projection("uk.yml", "name").jsonObject
        val firstNames = name.getValue("first_name").jsonArray
        assertEquals(name.getValue("male_first_name"), firstNames[0])
        assertEquals(name.getValue("female_first_name"), firstNames[1])
        val norway = store.projection("es-AR.yml", "address").jsonObject
            .getValue("country_by_name").jsonObject.getValue("noruega").jsonPrimitive
        assertEquals(false, norway.isString)
        assertEquals(false, norway.boolean)
        assertEquals("false", payloadSources.single { it.path == "no.yml" }.parsedRootKey)
        assertEquals("kidmod10", store.projection("no.yml", "invoice").jsonObject
            .getValue("reference").jsonObject.getValue("check_digit_method").jsonPrimitive.content)
    }

    @Test
    fun sourceIdentityOverlayOrderAndLocaleMetadataAreRetained() {
        assertEquals(listOf("en", "fr", "fr-CA"), localeLayers.getValue("fr-CA"))
        assertEquals(listOf("en"), localeLayers.getValue("en"))
        fun priority(path: String, category: String) = payloadProjections.single {
            payloadSources[it.sourceId].path == path && it.category == category
        }.sourcePriority
        assertEquals(0, priority("de.yml", "name"))
        assertEquals(1, priority("en/name.yml", "name"))
        assertEquals(2, priority("en/cat.yml", "creature"))
        val metadata = PayloadStore().projection("en.yml", null).jsonObject
        assertEquals("AM", metadata.getValue("time").jsonObject.getValue("am").jsonPrimitive.content)
    }

    @Test
    fun japaneseAddressUsesMultipleChunksAndPreservesUnicode() {
        val entry = payloadProjections.single {
            payloadSources[it.sourceId].path == "ja/address.yml" && it.category == "address"
        }
        assertTrue(entry.chunks > 1)
        val address = PayloadStore().projection("ja/address.yml", "address").jsonObject
        assertEquals(listOf("市", "区", "町", "村", "郡"),
            address.getValue("city_suffix").jsonArray.map { it.jsonPrimitive.content })
        assertTrue(address.getValue("postcode").jsonArray.size > 100_000)
    }

    private fun assertCanonicalKeys(value: JsonElement) {
        when (value) {
            is JsonObject -> {
                assertEquals(value.keys.sorted(), value.keys.toList())
                value.values.forEach(::assertCanonicalKeys)
            }
            is JsonArray -> value.forEach(::assertCanonicalKeys)
            else -> Unit
        }
    }
}
