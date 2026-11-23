package probe

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue
import probe.random.RandomSampling
import probe.random.RandomWords
import probe.regex.PortableRegex
import probe.regex.RegexGenerationException

class PortableRegexTest {
    @Test
    fun seededRegexResultsMatchIndependentReference() {
        // Independently expanded grammar choices using random_reference.py's integer oracle.
        val seeds = listOf(0L, 1L, -1L, Long.MIN_VALUE, Long.MAX_VALUE, 0x0123456789abcdefL)
        val expected = listOf(
            "1-\naaau\nAKIA0a6/\nx?}lr@y\n東京😀\n7d29825c75521255",
            "02-z\naaA\nAKIAi),9\nxY{yuy\n東京😀\nd0bad0da572baaf1",
            "AB-\naaR\nAKIA<C)i\nxmWIzb2y\n東京😀\n312b54a59456e8d2",
            "111-x\naaa@\nAKIAc)'~\nxzN_9fy\n東京😀\nbc88d755ade88590",
            "2-x\naaat\nAKIA)R84\nx+5eBBhq\${*IUUQAy\n東京😀\n9335ea7b1acdd29a",
            "222-\naaaa~\nAKIA~dPm\nx,8%7Q9BbG,y\n東京😀\nf7e9ff7484731d95",
        )
        val patterns = listOf("([A-C]{2}|[0-2]{1,3})-[x-z]?", "a{2,4}[^0-9]", """^AKIA\S{4}$""", "x.*y", "東京😀")
        seeds.forEachIndexed { index, seed ->
            val random = RandomSampling(seed)
            val generator = PortableRegex(random)
            val actual = patterns.map { generator.generate(it) } + random.nextLong().toULong().toString(16).padStart(16, '0')
            assertEquals(expected[index], actual.joinToString("\n"), "seed=$seed")
        }
    }

    @Test
    fun supportedGrammarProducesIndependentRegexMatches() {
        val patterns = listOf(
            "", "東京😀", "^abc$", "(?:ab|cd)(x|y)", "(|a)()",
            "[a-cba]", "[-a]", "[a-]", "[a&b]", "[^a-z]", "[\u0100-\u01ff]",
            """[\dA-C_\-]""", """[\\\]\[\^]""",
            """\d\D\w\W\s\S""", """\t\n\r\f\v""", ".",
            """\\\.\[\]\{\}\(\)\*\+\?\|\^\$\/""",
            "a?", "b*", "c+", "(ab){0}", "(xy){2}", "[a-z]{1,4}",
            """^AKIA\S{16}$""", """^\S{40}$""",
            """^i-0[\da-f]{16}$""", """^arn:.+:.+:.*:([0-9]{12}):(.+)$""",
        )
        for (seed in listOf(0L, 1L, -1L, Long.MIN_VALUE, Long.MAX_VALUE)) {
            val generator = PortableRegex(RandomSampling(seed))
            for (pattern in patterns) {
                repeat(8) {
                    val generated = generator.generate(pattern)
                    // Explicit full anchoring makes JS backtrack past a shorter/empty alternative.
                    assertTrue(Regex("^(?:$pattern)$").matches(generated), "pattern=$pattern result=$generated")
                }
            }
        }
    }

    @Test
    fun parsingAndLiteralNodesDoNotConsumeRandomness() {
        val generator = PortableRegex(RandomSampling(RandomWords { error("unexpected draw") }))
        assertEquals("ab.\n😀", generator.generate("^ab\\.\\n😀$"))
        assertEquals("", generator.generate("()a{0}(?:)"))
        assertEquals("aaaa", generator.generate("(a{2}){2}"))
    }

    @Test
    fun choicesRepetitionAndClassOrderHaveSpecifiedConsumption() {
        val words = ScriptedWords(listOf(0uL, 0uL, 0uL, 1uL, 1uL, 3uL, 4uL, 5uL))
        val generator = PortableRegex(RandomSampling(words))
        assertEquals("aa", generator.generate("[a]{2}"))
        assertEquals(2, words.consumed)
        assertEquals("", generator.generate("(a|b){0,1}"))
        assertEquals(3, words.consumed)
        assertEquals("b", generator.generate("(a|b)?"))
        assertEquals(5, words.consumed)
        assertEquals("abc", generator.generate("[a-cba]{3}"))
        assertEquals(8, words.consumed)
    }

    @Test
    fun repetitionAndAlphabetBoundsAreInclusive() {
        val generator = PortableRegex(RandomSampling(ScriptedWords(listOf(17uL, 16uL, 0uL, 15uL, 3uL, 5uL, 0uL, 255uL))))
        assertEquals("", generator.generate("a*"))
        assertEquals("a".repeat(16), generator.generate("a*"))
        assertEquals("a", generator.generate("a+"))
        assertEquals("a".repeat(16), generator.generate("a+"))
        assertEquals("aa", generator.generate("a{2,4}"))
        assertEquals("aaaa", generator.generate("a{2,4}"))
        assertEquals("\u0100", generator.generate("[\u0100-\u01ff]"))
        assertEquals("\u01ff", generator.generate("[\u0100-\u01ff]"))
    }

    @Test
    fun unsupportedOrMalformedPatternsFailBeforeAnyDraw() {
        val patterns = listOf(
            "(", ")", "[", "[]", "[^]", "[a", "[a-", "[[a]]", "[a&&b]",
            "[z-a]", """[\d-a]""", """[a-\d]""", "[a-\\", "[a-[]",
            "[\ud800]", "[\ud7ff-\ue000]", "[\u0100-\u0200]", "[^ -~]",
            "\\", "\\q", "\\p{L}", "\\u0041", "\\1", "\\-",
            "^a^", "a\$b", "(a$)", "^*", "*a", "+a", "?a", "{1}a", "a}", "a]",
            "a{}", "a{,2}", "a{2,}", "a{3,2}", "a{2", "a{2,3", "a{2x}",
            "a{4097}", "a{999999999999999999999}", "a??", "a*?", "a++",
            "(?=a)", "(?!a)", "(?<=a)", "(?<x>a)", "(?i:a)", "(?>a)", "(?",
            "\ud800", "\ud800a", "\udc00", "good|(?=bad)",
        )
        val generator = PortableRegex(RandomSampling(RandomWords { error("random consumed before rejection") }))
        for (pattern in patterns) {
            val failure = assertFailsWith<RegexGenerationException>(pattern) { generator.generate(pattern) }
            assertTrue(failure.position in 0..pattern.length, pattern)
            assertTrue(failure.message!!.startsWith("Regex position "), pattern)
        }
        val failure = assertFailsWith<RegexGenerationException> { generator.generate("ab\\q") }
        assertEquals(2, failure.position)
        assertTrue(failure.message!!.contains("unsupported escape"))
    }

    @Test
    fun parserAndOutputLimitsFailBeforeAnyDraw() {
        val generator = PortableRegex(RandomSampling(RandomWords { error("unexpected draw") }))
        assertEquals("a".repeat(4096), generator.generate("a{4096}"))
        assertEquals("", generator.generate("a{0}".repeat(4096)))
        assertEquals("a", generator.generate("(".repeat(64) + "a" + ")".repeat(64)))
        assertEquals("", generator.generate("(){4096}".repeat(15) + "(){4079}"))
        for (pattern in listOf(
            "a".repeat(16385), "a".repeat(4097), "(a{4096}){2}",
            "(".repeat(65) + "a" + ")".repeat(65), "😀{2049}",
            "((){4096}){4096}", "(){4096}".repeat(15) + "(){4080}",
        )) {
            assertFailsWith<RegexGenerationException> { generator.generate(pattern) }
        }
    }

    private class ScriptedWords(private val values: List<ULong>) : RandomWords {
        var consumed = 0
            private set

        override fun nextWord(): ULong {
            check(consumed < values.size) { "unexpected draw" }
            return values[consumed++]
        }
    }
}
