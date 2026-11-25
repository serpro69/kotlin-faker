package consumer

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class PublishedProbeTest {
    @Test
    fun publishedPayloadContainsEveryProjection() {
        val probe = createProbe(0)
        assertEquals(326, probe.sourceCount)
        assertEquals(59, probe.localeCount)
        assertEquals(666, probe.projectionCount)
        val manifest = buildString {
            repeat(probe.projectionCount) { index ->
                val path = probe.sourcePath(index)
                val category = probe.category(index)
                val encoded = probe.encodedProjection(index)
                // Decode each entry in a separate store: ordinary lookups need not load all data.
                assertEquals(encoded, createProbe(0).decodedProjection(path, category), path)
                append("$path\t${category ?: "-"}\t${encoded.length}\t${fingerprint(encoded)}\n")
            }
        }
        // Fold of Task 2's pre-compilation expectations, independently computed in Python.
        // Fixed here, never generated from the artifact under test or added as a main dependency.
        assertEquals("14376e443dcf410f", fingerprint(manifest))
        assertEquals(0, probe.decodedCount)
    }

    @Test
    fun publishedDecoderIsLazyAndPreservesUnicode() {
        val probe = createProbe(0)
        assertEquals(0, probe.decodedCount)
        val names = probe.decodedProjection("de.yml", "name")
        assertTrue(names.contains("\"first_name\":"))
        assertEquals(1, probe.decodedCount)
        assertEquals(names, probe.decodedProjection("de.yml", "name"))
        assertEquals(1, probe.decodedCount)
        val address = probe.decodedProjection("ja/address.yml", "address")
        assertTrue(address.contains("\"city_suffix\":[\"市\",\"区\",\"町\",\"村\",\"郡\"]"))
        assertEquals(2, probe.decodedCount)
        assertFailsWith<IllegalStateException> { probe.decodedProjection("missing.yml", "name") }
        assertEquals(2, probe.decodedCount)
        assertEquals("{\"time\":{\"am\":\"AM\",\"formats\":{\"us\":\"%m/%d/%Y %I:%M %p\"},\"pm\":\"PM\"}}", probe.decodedProjection("en.yml", null))
        assertEquals(3, probe.decodedCount)
    }

    @Test
    fun publishedSamplingMatchesIndependentVectors() {
        // First three SplitMix64(seed=0) words from Task 25's independent integer oracle.
        val probe = createProbe(0)
        assertEquals(-2152535657050944081L, probe.nextLong())
        assertEquals(0, probe.nextInt(10))
        assertEquals(0.026433771592597743, probe.nextDouble())
        assertFailsWith<IllegalArgumentException> { probe.nextInt(0) }
        assertEquals(-537132696929009172L, probe.nextLong())
        assertEquals(-2152535657050944081L, createProbe(0).nextLong())
    }

    @Test
    fun publishedRegexMatchesIndependentVectorsAndSemantics() {
        val probe = createProbe(0)
        val patterns = listOf("([A-C]{2}|[0-2]{1,3})-[x-z]?", "a{2,4}[^0-9]", "^AKIA\\S{4}$", "x.*y", "東京😀")
        val expected = listOf("1-", "aaau", "AKIA0a6/", "x?}lr@y", "東京😀")
        for ((index, pattern) in patterns.withIndex()) {
            val actual = probe.regexify(pattern)
            assertEquals(expected[index], actual)
            assertTrue(Regex("^(?:$pattern)$").matches(actual), pattern)
        }
        assertEquals(0x7d29825c75521255L, probe.nextLong())
    }

    @Test
    fun publishedFailuresPreserveTheRandomStream() {
        val probe = createProbe(-1)
        val error = assertFailsWith<IllegalArgumentException> { probe.regexify("(?=a)a") }
        assertTrue(error.message.orEmpty().contains("position"))
        assertEquals(-1956407806741107680L, probe.nextLong())
    }

    private fun fingerprint(value: String): String {
        var hash = 0xcbf29ce484222325uL
        for (char in value) hash = (hash xor char.code.toULong()) * 0x100000001b3uL
        return hash.toString(16).padStart(16, '0')
    }
}
