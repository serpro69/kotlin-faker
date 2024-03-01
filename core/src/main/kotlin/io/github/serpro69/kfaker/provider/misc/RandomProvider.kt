package io.github.serpro69.kfaker.provider.misc

import io.github.serpro69.kfaker.FakerConfig
import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.IRandom
import io.github.serpro69.kfaker.RandomService
import io.github.serpro69.kfaker.dictionary.Category
import io.github.serpro69.kfaker.provider.AbstractFakeDataProvider
import io.github.serpro69.kfaker.provider.misc.RandomProvider.Key.NEXT_CHAR
import io.github.serpro69.kfaker.provider.misc.RandomProvider.Key.NEXT_DOUBLE
import io.github.serpro69.kfaker.provider.misc.RandomProvider.Key.NEXT_ENUM
import io.github.serpro69.kfaker.provider.misc.RandomProvider.Key.NEXT_FLOAT
import io.github.serpro69.kfaker.provider.misc.RandomProvider.Key.NEXT_INT
import io.github.serpro69.kfaker.provider.misc.RandomProvider.Key.NEXT_LETTER
import io.github.serpro69.kfaker.provider.misc.RandomProvider.Key.NEXT_LONG
import io.github.serpro69.kfaker.provider.misc.RandomProvider.Key.NEXT_UUID
import io.github.serpro69.kfaker.provider.misc.RandomProvider.Key.RANDOM_DATE
import io.github.serpro69.kfaker.provider.misc.RandomProvider.Key.RANDOM_STRING
import io.github.serpro69.kfaker.provider.misc.RandomProvider.Key.RANDOM_SUBLIST
import io.github.serpro69.kfaker.provider.misc.RandomProvider.Key.RANDOM_SUBSET
import io.github.serpro69.kfaker.provider.misc.RandomProvider.Key.RANDOM_VALUE
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.*

/**
 * Provides data-generator-like functionality for the functions of [RandomService].
 */
class RandomProvider internal constructor(
    fakerService: FakerService
) : IRandom, AbstractFakeDataProvider<RandomProvider>(fakerService) {
    override val category: Category = Category.ofName("RANDOM")
    override val localUniqueDataProvider = LocalUniqueDataProvider<RandomProvider>()
    override val unique: RandomProvider by UniqueProviderDelegate(localUniqueDataProvider, fakerService)
    override val config: FakerConfig = fakerService.faker.config
    private val rs = fakerService.randomService

    /**
     * Clears used unique values for the function [key] of this provider.
     */
    fun clear(key: Key) = localUniqueDataProvider.clear(key.name)

    override fun nextInt(): Int = resolveUnique(NEXT_INT) { rs.nextInt() }

    override fun nextInt(bound: Int): Int = resolveUnique(NEXT_INT) { rs.nextInt(bound) }

    override fun nextInt(intRange: IntRange): Int = resolveUnique(NEXT_INT) { rs.nextInt(intRange) }

    override fun nextInt(min: Int, max: Int): Int = resolveUnique(NEXT_INT) { rs.nextInt(min = min, max = max) }

    override fun <T> randomValue(list: List<T>): T = resolveUnique(RANDOM_VALUE) { rs.randomValue(list) }

    override fun <T> randomValue(array: Array<T>): T = resolveUnique(RANDOM_VALUE) { rs.randomValue(array) }

    override fun nextLetter(upper: Boolean): Char = resolveUnique(NEXT_LETTER) { rs.nextLetter(upper) }

    override fun randomString(length: Int, numericalChars: Boolean): String {
        return resolveUnique(RANDOM_STRING) { rs.randomString(length = length, numericalChars = numericalChars) }
    }

    /**
     * Returns the next pseudorandom, uniformly distributed [Boolean] value
     * from this random number generator's sequence.
     *
     * The values `true` and `false` are produced with (approximately) equal probability.
     *
     * _NB! this method does not try to produce "unique" values via [unique] data provider._
     */
    override fun nextBoolean(): Boolean = rs.nextBoolean()

    override fun nextLong(): Long = resolveUnique(NEXT_LONG) { rs.nextLong() }

    override fun nextLong(bound: Long): Long = resolveUnique(NEXT_LONG) { rs.nextLong(bound) }

    override fun nextLong(longRange: LongRange): Long = resolveUnique(NEXT_LONG) { rs.nextLong(longRange) }

    override fun nextLong(min: Long, max: Long): Long = resolveUnique(NEXT_LONG) { rs.nextLong(min, max) }

    override fun nextFloat(): Float = resolveUnique(NEXT_FLOAT) { rs.nextFloat() }

    override fun nextDouble(): Double = resolveUnique(NEXT_DOUBLE) { rs.nextDouble() }

    override fun nextChar(): Char = resolveUnique(NEXT_CHAR) { rs.nextChar() }

    @Deprecated(
        message = "This function is deprecated and will be removed in future releases.\n" +
            "Note that default value for 'length' param has changed from '100' to '24' in the new 'randomString' function.",
        replaceWith = ReplaceWith("randomString"),
        level = DeprecationLevel.WARNING
    )
    override fun nextString(
        length: Int,
        locale: Locale,
        auxiliaryChars: Boolean,
        numericalChars: Boolean
    ): String = resolveUnique(RANDOM_STRING) {
        rs.randomString(
            length = length,
            locale = locale,
            indexChars = auxiliaryChars,
            auxiliaryChars = numericalChars
        )
    }

    override fun randomString(
        length: Int,
        locale: Locale,
        indexChars: Boolean,
        auxiliaryChars: Boolean,
        punctuationChars: Boolean,
        numericalChars: Boolean
    ): String = resolveUnique(RANDOM_STRING) {
        rs.randomString(
            length = length,
            locale = locale,
            indexChars = indexChars,
            auxiliaryChars = auxiliaryChars,
            punctuationChars = punctuationChars,
            numericalChars = numericalChars
        )
    }

    override fun randomString(
        min: Int,
        max: Int,
        locale: Locale,
        indexChars: Boolean,
        auxiliaryChars: Boolean,
        punctuationChars: Boolean,
        numericalChars: Boolean
    ): String = resolveUnique(RANDOM_STRING) {
        rs.randomString(
            min = min,
            max = max,
            locale = locale,
            indexChars = indexChars,
            auxiliaryChars = auxiliaryChars,
            punctuationChars = punctuationChars,
            numericalChars = numericalChars
        )
    }

    // copy-pasta from RandomService due to need for reified
    /**
     * Returns a randomly selected enum entry of type [E].
     *
     * _NB! when used with [unique], the [nextInt], which is used to get a random index of the [enumValues] of [E]
     * will also use unique generation and will need to be reset via [clear] as well when needed._
     */
    inline fun <reified E : Enum<E>> nextEnum(): E = resolveUnique(NEXT_ENUM) {
        val x: Int = nextInt(enumValues<E>().size)
        enumValues<E>()[x]
    }

    override fun <E : Enum<E>> nextEnum(enum: Class<E>): E = resolveUnique(NEXT_ENUM) { rs.nextEnum(enum) }

    override fun <E : Enum<E>> nextEnum(values: Array<E>): E = resolveUnique(NEXT_ENUM) { rs.nextEnum(values) }

    override fun <E : Enum<E>> nextEnum(enum: Class<E>, predicate: (E) -> Boolean): E {
        return resolveUnique(NEXT_ENUM) { rs.nextEnum(enum, predicate) }
    }

    // copy-pasta from RandomService due to need for reified
    /**
     * Returns a randomly selected enum entry of type [E] excluding a particular enum class by its name.
     *
     * _NB! when used with [unique], the [nextInt], which is used to get a random index of the [enumValues] of [E]
     * will also use unique generation and will need to be reset via [clear] as well when needed._
     */
    inline fun <reified E : Enum<E>> nextEnum(vararg excludeName: String): E {
        do {
            val e: E = nextEnum()
            if (excludeName.none { it.lowercase() == e.name.lowercase() }) {
                return e
            }
        } while (true)
    }

    override fun nextUUID(): String = resolveUnique(NEXT_UUID) { rs.nextUUID() }

    override fun <T> randomSublist(list: List<T>, size: Int, shuffled: Boolean): List<T> {
        return resolveUnique(RANDOM_SUBLIST) { rs.randomSublist(list = list, size = size, shuffled = shuffled) }
    }

    override fun <T> randomSublist(list: List<T>, sizeRange: IntRange, shuffled: Boolean): List<T> {
        return resolveUnique(RANDOM_SUBLIST) { rs.randomSublist(list = list, sizeRange = sizeRange, shuffled = shuffled) }
    }

    override fun <T> randomSubset(set: Set<T>, size: Int, shuffled: Boolean): Set<T> {
        return resolveUnique(RANDOM_SUBSET) { rs.randomSubset(set = set, size = size, shuffled = shuffled) }
    }

    override fun <T> randomSubset(set: Set<T>, sizeRange: IntRange, shuffled: Boolean): Set<T> {
        return resolveUnique(RANDOM_SUBSET) { rs.randomSubset(set = set, sizeRange = sizeRange, shuffled = shuffled) }
    }

    override fun nextPastDate(): OffsetDateTime = resolveUnique(RANDOM_DATE) { rs.nextPastDate() }

    override fun nextFutureDate(): OffsetDateTime = resolveUnique(RANDOM_DATE) { rs.nextFutureDate() }

    override fun nextDate(min: Instant, max: Instant, zoneOffset: ZoneOffset): OffsetDateTime =
        resolveUnique(RANDOM_SUBSET) { rs.nextDate(min, max, zoneOffset) }

    @PublishedApi
    internal fun <T> resolveUnique(key: Key, f: () -> T): T = resolveUniqueValue(key.name, f)

    /**
     * Keys for [unique] data provider to simplify resetting unique values via [RandomProvider.clear] function.
     */
    enum class Key {
        /**
         * Key for [nextInt] function
         */
        NEXT_INT,

        /**
         * Key for [randomValue] function
         */
        RANDOM_VALUE,

        /**
         * Key for [nextLetter] function
         */
        NEXT_LETTER,

        /**
         * Key for [randomString] function
         */
        RANDOM_STRING,

        /**
         * Key for [nextLong] function
         */
        NEXT_LONG,

        /**
         * Key for [nextFloat] function
         */
        NEXT_FLOAT,

        /**
         * Key for [nextDouble] function
         */
        NEXT_DOUBLE,

        /**
         * Key for [nextChar] function
         */
        NEXT_CHAR,

        /**
         * Key for [nextEnum] function
         */
        NEXT_ENUM,

        /**
         * Key for [nextUUID] function
         */
        NEXT_UUID,

        /**
         * Key for [randomSublist] function
         */
        RANDOM_SUBLIST,

        /**
         * Key for [randomSubset] function
         */
        RANDOM_SUBSET,

        /**
         * Key for [randomDate] function
         */
        RANDOM_DATE,
        ;
    }
}
