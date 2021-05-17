package io.github.serpro69.kfaker

import java.util.*

/**
 * Wrapper around [Random] that contains some extra helper functions.
 */
class RandomService internal constructor(private val random: Random) {
    private val alphabeticSource = "abcdefghijklmnopqrstuvwxyz"

    fun nextInt() = random.nextInt()

    fun nextInt(bound: Int) = random.nextInt(bound)

    fun nextInt(intRange: IntRange): Int {
        val lowerBound = requireNotNull(intRange.minOrNull())
        val upperBound = requireNotNull(intRange.maxOrNull())

        return nextInt(lowerBound, upperBound)
    }

    fun nextInt(min: Int, max: Int) = random.nextInt(max - min + 1) + min

    fun <T> randomValue(list: List<T>) = list[nextInt(list.size)]

    fun <T> randomValue(array: Array<T>) = array[nextInt(array.size)]

    fun nextLetter(upper: Boolean): Char {
        val source = if (upper) alphabeticSource.uppercase() else alphabeticSource

        return source[nextInt(source.length)]
    }

    fun nextBoolean() = random.nextBoolean()

    fun nextLong() = random.nextLong()

    fun nextFloat() = random.nextFloat()

    fun nextDouble() = random.nextDouble()

    fun nextChar() = random.nextInt().toChar()

    fun nextString() = List(100) { nextInt().toChar().toString() }.joinToString("")
}
