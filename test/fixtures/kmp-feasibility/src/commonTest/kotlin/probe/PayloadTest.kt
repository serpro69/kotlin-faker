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
import probe.payload.generatedExpectations
import probe.payload.localeLayers
import probe.payload.payloadProjections
import probe.payload.payloadSources

internal data class PayloadExpectation(
    val sourcePath: String,
    val category: String?,
    val utf16Length: Int,
    val checksum: ULong,
)

class PayloadTest {
    @Test
    fun everyProjectionRoundTripsOnThisTarget() {
        assertEquals(326, payloadSources.size)
        assertEquals(59, localeLayers.size)
        val keys = payloadProjections.map { payloadSources[it.sourceId].path to it.category }
        val expected = generatedExpectations().associateBy { it.sourcePath to it.category }
        assertEquals(keys.size, keys.toSet().size)
        assertEquals(keys.toSet(), expected.keys)
        assertEquals(payloadSources.indices.toSet(), payloadProjections.map { it.sourceId }.toSet())
        // Independently calculated vectors include non-ASCII, a surrogate pair, dollar,
        // backslash and NUL. Check the verifier before trusting its whole-corpus results.
        assertEquals(0xcbf29ce484222325uL, checksum(""))
        assertEquals(0x85944171f73967e8uL, checksum("foobar"))
        assertEquals(0x79319608151cd730uL, checksum("東京"))
        assertEquals(0xe296218fb8c1ff82uL, checksum("🙂$\\\u0000"))
        for (entry in payloadProjections) {
            val source = payloadSources[entry.sourceId]
            val reference = expected.getValue(source.path to entry.category)
            val encoded = entry.json()
            assertEquals(reference.utf16Length, encoded.length, source.path)
            assertEquals(reference.checksum, checksum(encoded), "${source.path} / ${entry.category}")
            val store = PayloadStore()
            val decoded = store.projection(source.path, entry.category)
            assertEquals(encoded, decoded.toString(), "${source.path} / ${entry.category}")
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
        fun kindRank(path: String, category: String) = payloadProjections.single {
            payloadSources[it.sourceId].path == path && it.category == category
        }.sourceKindRank
        assertEquals(0, kindRank("de.yml", "name"))
        assertEquals(1, kindRank("en/name.yml", "name"))
        assertEquals(2, kindRank("en/cat.yml", "creature"))
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

    private fun checksum(value: String): ULong {
        var hash = 0xcbf29ce484222325uL
        for (char in value) hash = (hash xor char.code.toULong()) * 0x100000001b3uL
        return hash
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
