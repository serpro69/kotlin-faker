package io.github.serpro69.kfaker

import com.ibm.icu.text.UnicodeSet
import com.ibm.icu.util.LocaleData
import com.ibm.icu.util.ULocale
import java.util.*
import kotlin.experimental.and
import kotlin.experimental.or
import kotlin.random.asKotlinRandom

/**
 * Wrapper around [Random] that also contains some additional functions not covered by [Random].
 *
 * If two instances of this [RandomService] are created with the same seed,
 * and the same sequence of method calls is made for each,
 * then they will generate and return identical sequences of values.
 *
 * Instances of [RandomService] are not cryptographically secure by default.
 * Consider passing [java.security.SecureRandom] to the constructor of this [RandomService]
 * to get a cryptographically secure pseudo-random generator.
 */
class RandomService internal constructor(private val config: FakerConfig) {
    private val random = config.random
    private val alphabeticLowerCharset = ('a'..'z')
    private val alphabeticUpperCharset = ('A'..'Z')
    private val numericCharset = ('0'..'9')

    /**
     * Returns the next pseudorandom, uniformly distributed [Int] value from this [random] number generator's sequence.
     */
    fun nextInt() = random.nextInt()

    /**
     * Returns a pseudorandom, uniformly distributed [Int] value between `0` (inclusive)
     * and the specified [bound] (exclusive),
     * drawn from this [random] number generator's sequence.
     */
    fun nextInt(bound: Int) = random.nextInt(bound)

    /**
     * Returns a pseudorandom, uniformly distributed [Int] value within the specified int [range] (inclusive),
     * drawn from this [random] number generator's sequence.
     */
    fun nextInt(intRange: IntRange): Int {
        val lowerBound = requireNotNull(intRange.minOrNull())
        val upperBound = requireNotNull(intRange.maxOrNull())

        return nextInt(lowerBound, upperBound)
    }

    /**
     * Returns a pseudorandom, uniformly distributed [Int] value between [min] (inclusive) and [max] (inclusive),
     * drawn from this [random] number generator's sequence.
     */
    fun nextInt(min: Int, max: Int) = random.nextInt(max - min + 1) + min

    /**
     * Returns a pseudo-randomly selected value from the [list] of values.
     */
    fun <T> randomValue(list: List<T>) = list[nextInt(list.size)]

    /**
     * Returns a pseudo-randomly selected value from the [array] of values.
     */
    fun <T> randomValue(array: Array<T>) = array[nextInt(array.size)]

    /**
     * Returns the next pseudorandom, uniformly distributed [Char] value
     * that corresponds to a letter in the English alphabet.
     *
     * @param upper returns the [Char] in upper-case if set to `true`, and in lower-case otherwise
     */
    fun nextLetter(upper: Boolean): Char {
        val source = if (upper) alphabeticUpperCharset else alphabeticLowerCharset
        return source.random(config.random.asKotlinRandom())
    }

    /**
     * Returns [String] with the specified [length] consisting of a pseudo-randomly generated
     * English alphabet letters and optional [numericalChars],
     * or an empty string for a `length < 1`.
     *
     * @param length         the length of the resulting string.
     *                       Default: `24`
     * @param numericalChars add additional numerical chars from 0 to 9 to the resulting string.
     *                       Default: `true`
     */
    fun randomString(length: Int = 24, numericalChars: Boolean = true): String {
        if (length < 1) return ""
        val charset = if (numericalChars) {
            alphabeticLowerCharset + alphabeticUpperCharset + numericCharset
        } else alphabeticLowerCharset + alphabeticUpperCharset
        return (1..length)
            .map { charset.random(this.random.asKotlinRandom()) }
            .joinToString("")
    }

    /**
     * Returns the next pseudorandom, uniformly distributed [Boolean] value
     * from this random number generator's sequence.
     *
     * The values `true` and `false` are produced with (approximately) equal probability.
     */
    fun nextBoolean() = random.nextBoolean()

    /**
     * Returns the next pseudorandom, uniformly distributed [Long] value from this [random] number generator's sequence.
     */
    fun nextLong() = random.nextLong()

    /**
     * Returns a pseudorandom, uniformly distributed [Long] value between `0` (inclusive),
     * and the specified [bound] value (exclusive),
     * drawn from this [random] number generator's sequence.
     *
     * @throws IllegalArgumentException if `bound < 0`
     */
    fun nextLong(bound: Long): Long {
        return if (bound > 0) {
            var value: Long

            do {
                val bits = (nextLong().shl(1)).shr(1)
                value = bits % bound
            } while (bits - value + (bound - 1) < 0L)
            value
        } else throw IllegalArgumentException("Bound bound must be greater than 0")
    }

    /**
     * Returns the next pseudorandom, uniformly distributed [Float] value between `0.0` and `1.0`
     * from this [random] number generator's sequence.
     */
    fun nextFloat() = random.nextFloat()

    /**
     * Returns the next pseudorandom, uniformly distributed [Double] value between `0.0` and `1.0`
     * from this [random] number generator's sequence.
     */
    fun nextDouble() = random.nextDouble()

    /**
     * Returns the next pseudorandom, uniformly distributed [Char] value,
     * from this [random] number generator's sequence.
     */
    fun nextChar() = nextInt().toChar()

    /**
     * Returns [String] with the specified [length] (or an empty string for a `length < 1`)
     * consisting of pseudo-randomly generated characters
     * in a given [locale] with optional [auxiliaryChars] and [numericalChars]
     *
     * @param length the length of the resulting string
     * @param locale locale to use to generate the charset. Defaults to `locale` config value set for the `faker` instance
     * @param auxiliaryChars add additional auxiliary chars to the resulting string as defined in [Character_Elements](https://www.unicode.org/reports/tr35/tr35-general.html#Character_Elements)
     * @param numericalChars add additional numerical chars from 0 to 9 to the resulting string
     */
    @Deprecated(
        message = "This function is deprecated and will be removed in future releases.\n" +
            "Note that default value for 'length' param has changed from '100' to '24' in the new 'randomString' function.",
        replaceWith = ReplaceWith("randomString"),
        level = DeprecationLevel.WARNING
    )
    fun nextString(
        length: Int = 100,
        locale: Locale = Locale.forLanguageTag(config.locale),
        auxiliaryChars: Boolean = false,
        numericalChars: Boolean = false
    ): String = randomString(length, locale, auxiliaryChars, numericalChars)

    /**
     * Returns [String] with the specified [length] (or an empty string for a `length < 1`)
     * consisting of pseudo-randomly generated characters
     * in a given [locale] with optional [auxiliaryChars] and [numericalChars]
     *
     * @param length         the length of the resulting string.
     *                       Default: `24`
     * @param locale         locale to use to generate the charset.
     *                       Defaults to `locale` config value set for the `faker` instance.
     * @param auxiliaryChars add additional auxiliary chars to the resulting string as defined in
     *                       [Character_Elements](https://www.unicode.org/reports/tr35/tr35-general.html#Character_Elements).
     *                       Default: `false`
     * @param numericalChars add additional numerical chars from 0 to 9 to the resulting string
     *                       Default: `false`
     */
    @JvmOverloads
    fun randomString(
        length: Int = 24,
        locale: Locale = Locale.forLanguageTag(config.locale),
        auxiliaryChars: Boolean = false,
        numericalChars: Boolean = false
    ): String {
        if (length < 1) return "" // base case
        if (locale in listOf(Locale.ENGLISH, Locale.US, Locale.UK, Locale.CANADA)) return randomString(
            length,
            numericalChars
        )

        val localeData = LocaleData.getInstance(ULocale.forLocale(locale))
        val mainChars = localeData.getExemplarSet(UnicodeSet.MIN_VALUE, LocaleData.ES_STANDARD)
            .ranges()
            .flatMap { (it.codepoint..it.codepointEnd).map { code -> Char(code) } }
        val auxChars = if (auxiliaryChars) {
            localeData.getExemplarSet(UnicodeSet.MIN_VALUE, LocaleData.ES_AUXILIARY)
                .ranges()
                .flatMap { (it.codepoint..it.codepointEnd).map { code -> Char(code) } }
        } else emptyList()
        val indexChars = localeData.getExemplarSet(UnicodeSet.MIN_VALUE, LocaleData.ES_INDEX)
            .ranges()
            .flatMap { (it.codepoint..it.codepointEnd).map { code -> Char(code) } }
        val numChars = if (numericalChars) numericCharset else emptyList()

        val chars = (mainChars + auxChars + indexChars + numChars)
        return List(length) { chars.random(random.asKotlinRandom()) }.joinToString("")
    }

    /**
     * Returns a randomly selected enum entry of type [E].
     */
    inline fun <reified E : Enum<E>> nextEnum(): E {
        val x: Int = nextInt(enumValues<E>().size)
        return enumValues<E>()[x]
    }

    /**
     * Returns a pseudo-randomly selected enum entry of type [E].
     */
    fun <E : Enum<E>> nextEnum(enum: Class<E>): E {
        return randomValue(enum.enumConstants)
    }

    /**
     * Returns a pseudo-randomly selected enum entry from an [Array] of [E] type [values].
     */
    fun <E : Enum<E>> nextEnum(values: Array<E>): E {
        return randomValue(values)
    }

    /**
     * Returns a pseudo-randomly selected [enum] class of type [E] based on [predicate] for [E].
     */
    tailrec fun <E : Enum<E>> nextEnum(enum: Class<E>, predicate: (E) -> Boolean): E {
        val enumClass = nextEnum(enum)
        return if (predicate(enumClass)) enumClass else nextEnum(enum, predicate)
    }

    /**
     * Returns a randomly selected enum entry of type [E] excluding a particular class by it's className.
     */
    inline fun <reified E : Enum<E>> nextEnum(excludeName: String): E {
        do {
            val e: E = nextEnum()
            if (e.name.lowercase() != excludeName.lowercase()) {
                return e
            }
        } while (true)
    }

    /**
     * Returns the next pseudorandom [UUID] as [String] taking the seed of this [random].
     */
    fun nextUUID(): String {
        val randomBytes = ByteArray(16)
        random.nextBytes(randomBytes)
        randomBytes[6] = randomBytes[6] and 0x0f // clear version
        randomBytes[6] = randomBytes[6] or 0x40 // set to version
        randomBytes[8] = randomBytes[8] and 0x3f // clear variant
        randomBytes[8] = randomBytes[8] or 0x80.toByte() // set to IETF variant
        return UUID.nameUUIDFromBytes(randomBytes).toString()
    }

    /**
     * Returns a view of the portion of the [list]
     * with pseudo-randomly generated `fromIndex` and (possibly) `toIndex` values.
     *
     * @param size the desired size of the resulting list.
     * If `size <= 0` then `toIndex` will also be randomly-generated.
     * @param shuffled if `true` the [list] will be shuffled before extracting the sublist
     */
    @JvmOverloads
    fun <T> randomSublist(list: List<T>, size: Int = 0, shuffled: Boolean = false): List<T> {
        val (from, to) = list.randomFromToIndices(size)
        return list.ifEmpty { emptyList() }
            .let { if (shuffled) it.shuffled(random) else it }
            .subList(from, to)
    }

    /**
     * Returns a portion of the [set]
     * with pseudo-randomly generated `fromIndex` and `toIndex` values.
     *
     * @param shuffled if `true` the [set] will be shuffled before extracting the subset
     */
    @JvmOverloads
    fun <T> randomSubset(set: Set<T>, size: Int = 0, shuffled: Boolean = false): Set<T> {
        val (from, to) = set.randomFromToIndices(size)
        return set.ifEmpty { emptyList() }
            .let { if (shuffled) it.shuffled(random) else it }
            .mapIndexedNotNull { i, v -> if (i in from until to) v else null }
            .toSet()
    }

    private fun <T> Collection<T>.randomFromToIndices(s: Int): Pair<Int, Int> {
        val fromIndex = if (s > 0) {
            (0..size - s).random(random.asKotlinRandom())
        } else {
            (indices).random(random.asKotlinRandom())
        }
        val toIndex = if (s > 0) {
            fromIndex + s
        } else {
            (fromIndex..size).random(random.asKotlinRandom())
        }

        return fromIndex to toIndex
    }
}
