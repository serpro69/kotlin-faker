package io.github.serpro69.kfaker

import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.*

/** Provides functionality similar to [Random] */
interface IRandom {
    val config: FakerConfig

    /**
     * Returns the next pseudorandom, uniformly distributed [Int] value from this [random] number
     * generator's sequence.
     */
    fun nextInt(): Int

    /**
     * Returns a pseudorandom, uniformly distributed [Int] value between `0` (inclusive) and the
     * specified [bound] (exclusive), drawn from this [random] number generator's sequence.
     */
    fun nextInt(bound: Int): Int

    /**
     * Returns a pseudorandom, uniformly distributed [Int] value within the specified int [range]
     * (inclusive), drawn from this [random] number generator's sequence.
     */
    fun nextInt(intRange: IntRange): Int

    /**
     * Returns a pseudorandom, uniformly distributed [Int] value between [min] (inclusive) and [max]
     * (inclusive), drawn from this [random] number generator's sequence.
     */
    fun nextInt(min: Int, max: Int): Int

    /** Returns a pseudo-randomly selected value from the [list] of values. */
    fun <T> randomValue(list: List<T>): T

    /** Returns a pseudo-randomly selected value from the [array] of values. */
    fun <T> randomValue(array: Array<T>): T

    /**
     * Returns the next pseudorandom, uniformly distributed [Char] value that corresponds to a
     * letter in the English alphabet.
     *
     * @param upper returns the [Char] in upper-case if set to `true`, and in lower-case otherwise
     */
    fun nextLetter(upper: Boolean): Char

    /**
     * Returns [String] with the specified [length] consisting of a pseudo-randomly generated
     * English alphabet letters and optional [numericalChars], or an empty string for a `length <
     * 1`.
     *
     * @param length the length of the resulting string. Default: `24`
     * @param numericalChars add additional numerical chars from 0 to 9 to the resulting string.
     *   Default: `true`
     */
    fun randomString(length: Int = 24, numericalChars: Boolean = true): String

    /**
     * Returns the next pseudorandom, uniformly distributed [Boolean] value from this random number
     * generator's sequence.
     *
     * The values `true` and `false` are produced with (approximately) equal probability.
     */
    fun nextBoolean(): Boolean

    /**
     * Returns the next pseudorandom, uniformly distributed [Long] value from this [random] number
     * generator's sequence.
     */
    fun nextLong(): Long

    /**
     * Returns a pseudorandom, uniformly distributed [Long] value between `0` (inclusive), and the
     * specified [bound] value (exclusive), drawn from this [random] number generator's sequence.
     *
     * @throws IllegalArgumentException if `bound < 0`
     */
    fun nextLong(bound: Long): Long

    /**
     * Returns a pseudorandom, uniformly distributed [Long] value within the specified [longRange]
     * (inclusive), drawn from this [random] number generator's sequence.
     */
    fun nextLong(longRange: LongRange): Long

    /**
     * Returns a pseudorandom, uniformly distributed [Long] value between [min] (inclusive) and
     * [max] (inclusive), drawn from this [random] number generator's sequence.
     */
    fun nextLong(min: Long, max: Long): Long

    /**
     * Returns the next pseudorandom, uniformly distributed [Float] value between `0.0` and `1.0`
     * from this [random] number generator's sequence.
     */
    fun nextFloat(): Float

    /**
     * Returns the next pseudorandom, uniformly distributed [Double] value between `0.0` and `1.0`
     * from this [random] number generator's sequence.
     */
    fun nextDouble(): Double

    /**
     * Returns the next pseudorandom, uniformly distributed [Char] value, from this [random] number
     * generator's sequence.
     */
    fun nextChar(): Char

    /**
     * Returns [String] with the specified [length] (or an empty string for a `length < 1`)
     * consisting of pseudo-randomly generated characters in a given [locale] with optional
     * [auxiliaryChars] and [numericalChars]
     *
     * @param length the length of the resulting string. Default: `24`
     * @param locale locale to use to generate the charset. Defaults to `locale` config value set
     *   for the `faker` instance.
     * @param indexChars add additional index chars to the resulting string, as defined in
     *   [Character_Elements](https://www.unicode.org/reports/tr35/tr35-general.html#Character_Elements).
     *   Default: `true`
     * @param auxiliaryChars add additional auxiliary chars to the resulting string as defined in
     *   [Character_Elements](https://www.unicode.org/reports/tr35/tr35-general.html#Character_Elements).
     *   Default: `false`
     * @param numericalChars add additional numerical chars from 0 to 9 to the resulting string
     *   Default: `false`
     */
    fun randomString(
        length: Int = 24,
        locale: Locale = Locale.forLanguageTag(config.locale),
        indexChars: Boolean = true,
        auxiliaryChars: Boolean = false,
        punctuationChars: Boolean = false,
        numericalChars: Boolean = false,
    ): String

    /**
     * Returns [String] with a randomLength withing the specified [min] and [max] boundaries (or an
     * empty string for if `randomLength < 1`) consisting of pseudo-randomly generated characters in
     * a given [locale] with optional [auxiliaryChars] and [numericalChars]
     *
     * @param min the minimum length of the resulting string.
     * @param max the maximum length of the resulting string.
     * @param locale locale to use to generate the charset. Defaults to `locale` config value set
     *   for the `faker` instance.
     * @param indexChars add additional index chars to the resulting string, as defined in
     *   [Character_Elements](https://www.unicode.org/reports/tr35/tr35-general.html#Character_Elements).
     *   Default: `true`
     * @param auxiliaryChars add additional auxiliary chars to the resulting string as defined in
     *   [Character_Elements](https://www.unicode.org/reports/tr35/tr35-general.html#Character_Elements).
     *   Default: `false`
     * @param numericalChars add additional numerical chars from 0 to 9 to the resulting string
     *   Default: `false`
     */
    fun randomString(
        min: Int,
        max: Int,
        locale: Locale = Locale.forLanguageTag(config.locale),
        indexChars: Boolean = true,
        auxiliaryChars: Boolean = false,
        punctuationChars: Boolean = false,
        numericalChars: Boolean = false,
    ): String

    /** Returns a pseudo-randomly selected enum entry of type [E]. */
    fun <E : Enum<E>> nextEnum(enum: Class<E>): E

    /** Returns a pseudo-randomly selected enum entry from an [Array] of [E] type [values]. */
    fun <E : Enum<E>> nextEnum(values: Array<E>): E

    /** Returns a pseudo-randomly selected [enum] class of type [E] based on [predicate] for [E]. */
    fun <E : Enum<E>> nextEnum(enum: Class<E>, predicate: (E) -> Boolean): E

    /** Returns the next pseudorandom [UUID] as [String] taking the seed of this [random]. */
    fun nextUUID(): String

    /**
     * Returns a view of the portion of the [list] with pseudo-randomly generated `fromIndex` and
     * (possibly) `toIndex` values.
     *
     * @param size the desired size of the resulting list. If `size <= 0` then `toIndex` will also
     *   be randomly-generated.
     * @param shuffled if `true` the [list] will be shuffled before extracting the sublist
     */
    fun <T> randomSublist(list: List<T>, size: Int = 0, shuffled: Boolean = false): List<T>

    /**
     * Returns a view of the portion of the [list] with pseudo-randomly generated `fromIndex` and
     * (possibly) `toIndex` values.
     *
     * @param sizeRange the desired size range of the resulting list. The `size` of the returned
     *   list is the result of calling [nextInt] with the [sizeRange]. IF `size <= 0` then `toIndex`
     *   will also be randomly-generated.
     * @param shuffled if `true` the [list] will be shuffled before extracting the sublist
     */
    fun <T> randomSublist(list: List<T>, sizeRange: IntRange, shuffled: Boolean = false): List<T>

    /**
     * Returns a portion of the [set] with pseudo-randomly generated `fromIndex` and (possibly)
     * `toIndex` values.
     *
     * @param size the desired size of the resulting set. If `size <= 0` then `toIndex` will also be
     *   randomly-generated.
     * @param shuffled if `true` the [set] will be shuffled before extracting the subset
     */
    fun <T> randomSubset(set: Set<T>, size: Int = 0, shuffled: Boolean = false): Set<T>

    /**
     * Returns a portion of the [set] with pseudo-randomly generated `fromIndex` and (possibly)
     * `toIndex` values.
     *
     * @param sizeRange the desired size range of the resulting list. The `size` of the returned
     *   list is the result of calling [nextInt] with the [sizeRange]. IF `size <= 0` then `toIndex`
     *   will also be randomly-generated.
     * @param shuffled if `true` the [set] will be shuffled before extracting the subset
     */
    fun <T> randomSubset(set: Set<T>, sizeRange: IntRange, shuffled: Boolean = false): Set<T>

    /**
     * Returns a pseudorandom, uniformly distributed [OffsetDateTime] value between Unix epoch
     * (1970-01-01T00:00:00Z) (inclusive) and now (exclusive) using UTC zone offset
     */
    fun randomPastDate(): OffsetDateTime

    /**
     * Returns a pseudorandom, uniformly distributed [OffsetDateTime] value between [min]
     * (inclusive) and now (exclusive) using UTC zone offset
     */
    fun randomPastDate(min: Instant): OffsetDateTime

    /**
     * Returns a pseudorandom, uniformly distributed [OffsetDateTime] value between now (exclusive)
     * and now + 50 years (inclusive) using UTC zone offset
     */
    fun randomFutureDate(): OffsetDateTime

    /**
     * Returns a pseudorandom, uniformly distributed [OffsetDateTime] value between now (exclusive)
     * and [max] (inclusive) using UTC zone offset
     */
    fun randomFutureDate(max: Instant): OffsetDateTime

    /**
     * Returns a pseudorandom, uniformly distributed [OffsetDateTime] value between [min]
     * (inclusive) and [max] (inclusive) using the defined [zoneOffset]
     */
    fun randomDate(min: Instant, max: Instant, zoneOffset: ZoneOffset): OffsetDateTime
}
