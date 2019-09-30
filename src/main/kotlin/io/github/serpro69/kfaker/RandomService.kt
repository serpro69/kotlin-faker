package io.github.serpro69.kfaker

import java.util.*

/**
 * Wrapper around [Random] that contains some extra helper functions.
 */
internal class RandomService(private val random: Random) {
    private val alphabeticSource = "abcdefghijklmnopqrstuvwxyz"

    fun nextInt() = random.nextInt()

    fun nextInt(bound: Int) = random.nextInt(bound)

    fun nextInt(intRange: IntRange): Int {
        val lowerBound = requireNotNull(intRange.min())
        val upperBound = requireNotNull(intRange.max())

        return nextInt(lowerBound, upperBound)
    }

    fun nextInt(min: Int, max: Int) = random.nextInt(max - min + 1) + min

    fun <T> randomValue(list: List<T>) = list[nextInt(list.size)]

    fun nextLetter(upper: Boolean): Char {
        val source = if (upper) alphabeticSource.toUpperCase() else alphabeticSource

        return source[nextInt(source.length)]
    }

    fun nextBoolean() = random.nextBoolean()

    fun nextLong() = random.nextLong()

    fun nextFloat() = random.nextFloat()

    fun nextDouble() = random.nextDouble()

    fun nextChar() = random.nextInt().toChar()

    fun nextString() = List(100) { nextInt().toChar().toString() }.joinToString("")
}
