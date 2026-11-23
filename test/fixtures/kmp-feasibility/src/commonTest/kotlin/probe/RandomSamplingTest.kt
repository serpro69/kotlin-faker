package probe

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue
import probe.random.RandomSampling
import probe.random.RandomWords
import probe.random.SplitMix64

class RandomSamplingTest {
    // Derived offline by random_reference.py using unbounded integers, not this Kotlin code.
    private val seeds = listOf(0L, 1L, -1L, Long.MIN_VALUE, Long.MAX_VALUE, 0x0123456789abcdefL)
    private val rawVectors = listOf(
        "e220a8397b1dcdaf 6e789e6aa1b965f4 06c45d188009454f f88bb8a8724c81ec 1b39896a51a8749b",
        "910a2dec89025cc1 beeb8da1658eec67 f893a2eefb32555e 71c18690ee42c90b 71bb54d8d101b5b9",
        "e4d971771b652c20 e99ff867dbf682c9 382ff84cb27281e9 6d1db36ccba982d2 b4a0472e578069ae",
        "481ec0a212a9f3db c46fa638a6309012 61a685ffc80a8140 592e268383e356f9 0c8881ee746884d3",
        "2a67d7552e039ea7 f20c01408082f947 ec159351af424190 2020319894995bfb 532168fd38c3f6cb",
        "157a3807a48faa9d d573529b34a1d093 2f90b72e996dccbe a2d419334c4667ec 01404ce914938008",
    )
    private val mixedVectors = listOf(
        "-501176263|7960286522194355700|9|4074553321498378729|-777489253|-3185277435591613718|-5|-1981044378|false|3f73b848|3fd95fbb374f2c4e|f6de5b05e026d3c27bdbbb|5,3,4,6,0,1,2,7|db01602b100b9ed7",
        "-1861603860|-4689498862643123097|0|3585294735394392330|1359066553|4849545566009754240|6|2995962045|false|3f4b435c|3fd9dd771dc05592|fe8bcf144dd4fc9ac05daa|6,4,7,1,0,5,3,2|10e2c46865e98746",
        "-455511689|-1612297016619662647|1|3250951785886089937|-679450194|5989134109488233267|-8|3916338322|true|3c479400|3f8d91a4b0f38e40|a75d02d3525975cedbba06|5,1,0,7,6,4,3,2|5d4bee7accc8783d",
        "1209974946|-4292029157624213486|4|1814430046172174072|-194476845|-3639354735105423873|-8|-232877561|true|3ec368a6|3fe14d7973e5c2a4|c6a59d0e994a4863a556ec|2,0,1,6,3,7,4,5|daa59e21440f1483",
        "711448405|-1005427240264861369|0|1378498397740463818|-1476468461|-5968338125684211929|-5|-642734457|true|3f561663|3fc17410ddb0fd14|30838a7b1f03bbaab3761e|0,1,6,4,5,2,3,7|2bf849ffb8cf19cd",
        "360331271|-3066016094752747373|6|2509658600465917930|-1802272760|-7729206875838002062|3|1290953518|true|3e1a0340|3fe9b71939b34c5b|ecad8950fd606e6a4777f7|6,2,4,0,5,7,1,3|5a2b349fbc1e0ffe",
    )

    @Test
    fun rawSeededWordsMatchIndependentReference() {
        seeds.forEachIndexed { index, seed ->
            val stream = SplitMix64(seed)
            assertEquals(rawVectors[index], List(5) { stream.nextWord().hex() }.joinToString(" "))
        }
    }

    @Test
    fun mixedOperationsMatchIndependentReference() {
        seeds.forEachIndexed { index, seed ->
            val random = RandomSampling(seed)
            val actual = listOf(
                random.nextInt().toString(),
                random.nextLong().toString(),
                random.nextInt(10).toString(),
                random.nextLong(4611686018427387905L).toString(),
                random.intInRange(Int.MIN_VALUE, Int.MAX_VALUE).toString(),
                random.longInRange(Long.MIN_VALUE, Long.MAX_VALUE).toString(),
                random.intInRange(-17, 19).toString(),
                random.longInRange(-4000000000L, 5000000000L).toString(),
                random.nextBoolean().toString(),
                random.nextFloat().toBits().toUInt().toString(16).padStart(8, '0'),
                random.nextDouble().toBits().toULong().hex(),
                random.nextBytes(11).joinToString("") { it.toUByte().toString(16).padStart(2, '0') },
                random.shuffled((0..7).toList()).joinToString(","),
                random.nextLong().toULong().hex(),
            )
            assertEquals(mixedVectors[index], actual.joinToString("|"), "seed=$seed")
        }
    }

    @Test
    fun rejectionUsesUnsignedThresholdAndConsumesEveryRejectedWord() {
        // 2^64 % 10 = 6. Reject 0 and 5; accept the threshold itself, then the maximum word.
        val words = ScriptedWords(listOf(0uL, 5uL, 6uL, ULong.MAX_VALUE, 10uL))
        val random = RandomSampling(words)
        assertEquals(6, random.nextInt(10))
        assertEquals(3, words.consumed)
        assertEquals(5L, random.nextLong(10))
        assertEquals(0, random.nextInt(10))
        assertEquals(5, words.consumed)

        // Large bound: threshold = 2^62 - 3, proving multiple rejections near the signed boundary.
        val large = ScriptedWords(listOf(0uL, 4611686018427387900uL, 4611686018427387901uL))
        assertEquals(4611686018427387901L, RandomSampling(large).nextLong(4611686018427387905L))
        assertEquals(3, large.consumed)

        // Width 2^64 - 1: only zero is rejected.
        val widest = ScriptedWords(listOf(0uL, 1uL, ULong.MAX_VALUE))
        val wideRandom = RandomSampling(widest)
        assertEquals(Long.MIN_VALUE + 1, wideRandom.longInRange(Long.MIN_VALUE, Long.MAX_VALUE - 1))
        assertEquals(Long.MIN_VALUE, wideRandom.longInRange(Long.MIN_VALUE, Long.MAX_VALUE - 1))
        // 2^64 % (2^31 - 1) = 4; 2^64 % (2^63 - 1) = 2.
        val intThreshold = ScriptedWords(listOf(3uL, 4uL))
        assertEquals(4, RandomSampling(intThreshold).nextInt(Int.MAX_VALUE))
        assertEquals(2, intThreshold.consumed)
        val longThreshold = ScriptedWords(listOf(1uL, 2uL))
        assertEquals(2L, RandomSampling(longThreshold).nextLong(Long.MAX_VALUE))
        assertEquals(2, longThreshold.consumed)
    }

    @Test
    fun signedRangesIncludeEndpointsWithoutOverflow() {
        val ints = RandomSampling(ScriptedWords(listOf(0uL, 0xffffffffuL, 0uL, 1uL)))
        assertEquals(Int.MIN_VALUE, ints.intInRange(Int.MIN_VALUE, Int.MAX_VALUE))
        assertEquals(Int.MAX_VALUE, ints.intInRange(Int.MIN_VALUE, Int.MAX_VALUE))
        assertEquals(Int.MAX_VALUE - 1, ints.intInRange(Int.MAX_VALUE - 1, Int.MAX_VALUE))
        assertEquals(Int.MAX_VALUE, ints.intInRange(Int.MAX_VALUE - 1, Int.MAX_VALUE))
        val longs = RandomSampling(ScriptedWords(listOf(0uL, ULong.MAX_VALUE, 0uL, 1uL)))
        assertEquals(Long.MIN_VALUE, longs.longInRange(Long.MIN_VALUE, Long.MAX_VALUE))
        assertEquals(Long.MAX_VALUE, longs.longInRange(Long.MIN_VALUE, Long.MAX_VALUE))
        assertEquals(Long.MIN_VALUE, longs.longInRange(Long.MIN_VALUE, Long.MIN_VALUE + 1))
        assertEquals(Long.MIN_VALUE + 1, longs.longInRange(Long.MIN_VALUE, Long.MIN_VALUE + 1))
        val crossing = RandomSampling(ScriptedWords(listOf(0uL, ULong.MAX_VALUE)))
        assertEquals(-1L, crossing.longInRange(-1, Long.MAX_VALUE - 1))
        assertEquals(Long.MAX_VALUE - 1, crossing.longInRange(-1, Long.MAX_VALUE - 1))
    }

    @Test
    fun unitAndPowerOfTwoBoundsStillConsumeOneWord() {
        val words = ScriptedWords(List(6) { ULong.MAX_VALUE })
        val random = RandomSampling(words)
        assertEquals(0, random.nextInt(1))
        assertEquals(0L, random.nextLong(1))
        assertEquals(-3, random.intInRange(-3, -3))
        assertEquals(Long.MAX_VALUE, random.longInRange(Long.MAX_VALUE, Long.MAX_VALUE))
        assertEquals(7, random.nextInt(8))
        assertEquals(4611686018427387903L, random.nextLong(4611686018427387904L))
        assertEquals(6, words.consumed)
    }

    @Test
    fun scalarBitConversionsHaveExactEndpoints() {
        val random = RandomSampling(ScriptedWords(listOf(
            0uL, ULong.MAX_VALUE, 0uL, ULong.MAX_VALUE,
            0x7fffffffffffffffuL, 0x8000000000000000uL,
            0uL, ULong.MAX_VALUE, 0uL, ULong.MAX_VALUE,
        )))
        assertEquals(0, random.nextInt())
        assertEquals(-1, random.nextInt())
        assertEquals(0L, random.nextLong())
        assertEquals(-1L, random.nextLong())
        assertEquals(false, random.nextBoolean())
        assertEquals(true, random.nextBoolean())
        assertEquals(0.0f, random.nextFloat())
        val maximumFloat = random.nextFloat()
        assertEquals(0x3f7fffff, maximumFloat.toBits())
        assertEquals(0.999999940395355224609375, maximumFloat.toDouble())
        assertEquals(0.0, random.nextDouble())
        assertEquals(0.9999999999999999, random.nextDouble())
    }

    @Test
    fun bytesAreLittleEndianAndDiscardUnusedWordSuffixes() {
        val words = ScriptedWords(listOf(0xffeeddccbbaa9988uL, 0x7766554433221100uL, 0x0102030405060708uL))
        val random = RandomSampling(words)
        assertContentEquals(byteArrayOf(), random.nextBytes(0))
        assertEquals(0, words.consumed)
        assertContentEquals(listOf(136, 153, 170, 187, 204, 221, 238, 255, 0).map { it.toByte() }.toByteArray(), random.nextBytes(9))
        assertEquals(2, words.consumed)
        assertContentEquals(byteArrayOf(8), random.nextBytes(1))
        assertEquals(3, words.consumed)
        val exact = ScriptedWords(listOf(0x0706050403020100uL))
        assertContentEquals(ByteArray(8) { it.toByte() }, RandomSampling(exact).nextBytes(8))
        assertEquals(1, exact.consumed)
    }

    @Test
    fun shuffleUsesDescendingFisherYatesAndPreservesInput() {
        val words = ScriptedWords(listOf(0uL, 0uL, 1uL, 0uL))
        val random = RandomSampling(words)
        val input = listOf("a", "b", "c", "d")
        assertEquals(emptyList(), random.shuffled(emptyList<String>()))
        assertEquals(listOf("a"), random.shuffled(listOf("a")))
        assertEquals(0, words.consumed)
        // i=3 chooses 0; i=2 rejects 0 and chooses 1; i=1 chooses 0.
        assertEquals(listOf("c", "d", "b", "a"), random.shuffled(input))
        assertEquals(listOf("a", "b", "c", "d"), input)
        assertEquals(4, words.consumed)
        val duplicates = listOf(1, 1, 2, 3, 3)
        assertEquals(duplicates, RandomSampling(42).shuffled(duplicates).sorted())
    }

    @Test
    fun invalidArgumentsFailBeforeConsumingRandomness() {
        val words = ScriptedWords(emptyList())
        val random = RandomSampling(words)
        for (bound in listOf(0, -1, Int.MIN_VALUE)) {
            assertFailsWith<IllegalArgumentException> { random.nextInt(bound) }
        }
        for (bound in listOf(0L, -1L, Long.MIN_VALUE)) {
            assertFailsWith<IllegalArgumentException> { random.nextLong(bound) }
        }
        assertFailsWith<IllegalArgumentException> { random.intInRange(1, 0) }
        assertFailsWith<IllegalArgumentException> { random.longInRange(Long.MAX_VALUE, Long.MIN_VALUE) }
        assertFailsWith<IllegalArgumentException> { random.nextBytes(-1) }
        assertEquals(0, words.consumed)
    }

    @Test
    fun seededSamplingSatisfiesRangeAndPermutationInvariants() {
        for (seed in seeds) {
            val random = RandomSampling(seed)
            repeat(128) {
                assertTrue(random.nextInt(Int.MAX_VALUE) in 0 until Int.MAX_VALUE)
                assertTrue(random.nextLong(Long.MAX_VALUE) in 0 until Long.MAX_VALUE)
                assertTrue(random.intInRange(-17, 19) in -17..19)
                assertTrue(random.longInRange(-4000000000L, 5000000000L) in -4000000000L..5000000000L)
                assertTrue(random.nextFloat() in 0.0f..<1.0f)
                assertTrue(random.nextDouble() in 0.0..<1.0)
                assertEquals((0..7).toList(), random.shuffled((0..7).toList()).sorted())
            }
        }
    }

    @Test
    fun sameSeedInstancesKeepIndependentState() {
        val first = RandomSampling(-42)
        val second = RandomSampling(-42)
        val expected = List(20) { first.nextLong() }
        assertEquals(expected, List(20) { second.nextLong() })
        assertEquals(first.nextLong(), second.nextLong())
    }

    private fun ULong.hex(): String = toString(16).padStart(16, '0')

    private class ScriptedWords(private val values: List<ULong>) : RandomWords {
        var consumed = 0
            private set

        override fun nextWord(): ULong {
            check(consumed < values.size) { "unexpected random consumption" }
            return values[consumed++]
        }
    }
}
