package probe

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonObject
import probe.payload.PayloadStore
import probe.payload.payloadProjections
import probe.payload.payloadSources
import probe.random.RandomSampling
import probe.random.RandomWords
import probe.regex.PortableRegex

internal data class RegexInput(val source: String, val key: String, val input: String, val pattern: String)

// Test inputs only: consume the already verified embedded projections, without provider logic.
internal val regexCorpus: List<RegexInput> by lazy {
    buildList {
        val store = PayloadStore()
        fun visit(value: JsonElement?, source: String, key: String, transform: (String) -> String?) {
            when (value) {
                null -> Unit
                is JsonObject -> value.forEach { (name, child) -> visit(child, source, "$key.$name", transform) }
                is JsonArray -> value.forEachIndexed { index, child -> visit(child, source, "$key[$index]", transform) }
                is JsonPrimitive -> {
                    check(value.isString) { "Non-string pattern at $source:$key" }
                    transform(value.content)?.let { add(RegexInput(source, key, value.content, it)) }
                }
            }
        }
        for (entry in payloadProjections) {
            if (entry.category !in setOf("address", "bank", "finance", "vehicle")) continue
            val source = payloadSources[entry.sourceId].path
            val locale = source.substringBefore('/').removeSuffix(".yml").removeSuffix(".yaml")
            val data = store.projection(source, entry.category).jsonObject
            when (entry.category) {
                "address" -> visit(data["postcode"], source, "$locale.address.postcode") {
                    if ('#' in it) null else it
                }
                "bank" -> data["iban_details"]?.jsonObject?.forEach { (country, details) ->
                    visit(details.jsonObject["bban_pattern"], source, "$locale.bank.iban_details.$country") { it }
                }
                "finance" -> visit(data["credit_card"], source, "$locale.finance.credit_card") { it.replace('#', '0') }
                "vehicle" -> visit(data["license_plate_by_state"], source, "$locale.vehicle.license_plate_by_state") {
                    it.replace('#', '0').replace('?', 'A')
                }
            }
        }
    }
}

class RegexCorpusTest {
    @Test
    fun everyBundledRegexPatternIsClassifiedAndGeneratesMatchingValues() {
        assertEquals(122923, regexCorpus.size)
        val distinct = regexCorpus.map { it.pattern }.distinct().sorted()
        assertEquals(122872, distinct.size)
        val syntax = distinct.filter { pattern -> pattern.any { it in "\\[]{}()*+?|.^$" } }.toSet()
        assertEquals(312, syntax.size)
        assertEquals(122560, distinct.size - syntax.size)
        val literalGenerator = PortableRegex(RandomSampling(RandomWords { error("literal consumed randomness") }))
        var fingerprint = 0xcbf29ce484222325uL
        for (pattern in distinct) {
            if (pattern !in syntax) {
                assertEquals(pattern, literalGenerator.generate(pattern), "literal pattern")
                continue
            }
            val matcher = Regex("^(?:$pattern)$")
            for (seed in listOf(0L, 1L, -1L)) {
                val generated = PortableRegex(RandomSampling(seed)).generate(pattern)
                assertTrue(matcher.matches(generated), "pattern=$pattern seed=$seed result=$generated")
                // Length prefix keeps concatenated outcomes unambiguous.
                for (char in generated.length.toString() + ":" + generated) {
                    fingerprint = (fingerprint xor char.code.toULong()) * 0x100000001b3uL
                }
            }
        }
        // Recorded from the JVM semantic run, then matched on Node.
        assertEquals(0x61af7582c14d505duL, fingerprint)
    }

    @Test
    fun cryptographyLengthsAndPreprocessingLiteralsArePreserved() {
        val generator = PortableRegex(RandomSampling(123))
        for (length in listOf(32, 40, 56, 64, 96, 128)) {
            val generated = generator.generate("[a-f0-9]{$length}")
            assertEquals(length, generated.length)
            assertTrue(generated.all { it in "abcdef0123456789" })
        }
        val finance = generator.generate("/30[0-5]0-000000-000L/")
        assertTrue(finance.startsWith("/30"))
        assertTrue(finance.endsWith("L/"))
        assertTrue(Regex("/30[0-5]0-000000-000L/").matches(finance))
        assertEquals("A000", generator.generate("A000"))
    }
}
