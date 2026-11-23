package probe.random

/**
 * Candidate sampling contract, independent of kotlin.random's derived algorithms.
 * Each scalar consumes a whole word, plus any rejected words. No unused bits are buffered.
 * Bounds are exclusive; the explicitly named *InRange operations include both endpoints.
 * See the Task 25 sampling specification in docs/feat/wip/kotlin-multiplatform/feasibility.md.
 */
internal class RandomSampling(private val words: RandomWords) {
    constructor(seed: Long) : this(SplitMix64(seed))

    fun nextInt(): Int = (words.nextWord() shr 32).toInt()

    fun nextLong(): Long = words.nextWord().toLong()

    fun nextBoolean(): Boolean = (words.nextWord() shr 63) != 0uL

    fun nextInt(bound: Int): Int {
        require(bound > 0) { "bound must be positive: $bound" }
        return below(bound.toULong()).toInt()
    }

    fun nextLong(bound: Long): Long {
        require(bound > 0) { "bound must be positive: $bound" }
        return below(bound.toULong()).toLong()
    }

    fun intInRange(first: Int, last: Int): Int {
        require(first <= last) { "empty Int range: $first..$last" }
        val width = (last.toLong() - first.toLong() + 1).toULong()
        return (first.toLong() + below(width).toLong()).toInt()
    }

    fun longInRange(first: Long, last: Long): Long {
        require(first <= last) { "empty Long range: $first..$last" }
        val width = last.toULong() - first.toULong() + 1uL
        // Only the complete signed Long interval has width 2^64, represented here by zero.
        val offset = if (width == 0uL) words.nextWord() else below(width)
        return (first.toULong() + offset).toLong()
    }

    fun nextFloat(): Float = (words.nextWord() shr 40).toInt() / 16777216.0f

    fun nextDouble(): Double = (words.nextWord() shr 11).toLong() / 9007199254740992.0

    fun nextBytes(size: Int): ByteArray {
        require(size >= 0) { "size must be nonnegative: $size" }
        val result = ByteArray(size)
        var index = 0
        while (index < size) {
            var word = words.nextWord()
            repeat(minOf(8, size - index)) {
                result[index++] = word.toByte()
                word = word shr 8
            }
        }
        return result
    }

    fun <T> shuffled(values: List<T>): List<T> {
        val result = values.toMutableList()
        for (index in result.lastIndex downTo 1) {
            val other = nextInt(index + 1)
            val value = result[index]
            result[index] = result[other]
            result[other] = value
        }
        return result
    }

    private fun below(bound: ULong): ULong {
        // Discard the short prefix so each residue has exactly floor(2^64 / bound) preimages.
        val threshold = (0uL - bound) % bound
        while (true) {
            val word = words.nextWord()
            if (word >= threshold) return word % bound
        }
    }
}
