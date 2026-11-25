package io.github.serpro69.kfaker.runtime

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class GenerationContractsTest {
    @Test
    fun configurationRetainsExplicitLocaleAndSeed() {
        val configured = GenerationConfiguration("fr_CA", Long.MIN_VALUE)
        assertEquals("fr_CA", configured.locale)
        assertEquals(Long.MIN_VALUE, configured.randomSeed)
        assertEquals(0L, GenerationConfiguration("en", 0L).randomSeed)
        assertNull(GenerationConfiguration("ja", null).randomSeed)
    }

    @Test
    fun resourceContractPreservesSourceAndCategoryIdentity() {
        val calls = mutableListOf<Pair<String, String?>>()
        val resources = DictionaryResources { source, category ->
            calls += source to category
            when (source to category) {
                "en/name.yml" to "name" -> "{\"first_name\":[\"A\"]}"
                "en.yml" to null -> "{\"time\":{\"am\":\"AM\"}}"
                else -> null
            }
        }
        assertEquals("{\"first_name\":[\"A\"]}", resources.projection("en/name.yml", "name"))
        assertEquals("{\"time\":{\"am\":\"AM\"}}", resources.projection("en.yml", null))
        assertNull(resources.projection("missing.yml", "name"))
        assertEquals(
            listOf("en/name.yml" to "name", "en.yml" to null, "missing.yml" to "name"),
            calls,
        )
    }

    @Test
    fun randomContractAcceptsAnIndependentCommonImplementation() {
        val bounds = mutableListOf<Int>()
        val random = RandomOperations { bound ->
            bounds += bound
            bound - 1
        }
        assertEquals(0, random.nextInt(1))
        assertEquals(9, random.nextInt(10))
        assertEquals(listOf(1, 10), bounds)
    }
}
