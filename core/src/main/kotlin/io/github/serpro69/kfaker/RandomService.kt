package io.github.serpro69.kfaker

import com.ibm.icu.text.UnicodeSet
import com.ibm.icu.util.LocaleData
import com.ibm.icu.util.ULocale
import java.time.Duration
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset
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
class RandomService internal constructor(override val config: FakerConfig) : IRandom {
    private val random = config.random
    private val alphabeticLowerCharset = ('a'..'z')
    private val alphabeticUpperCharset = ('A'..'Z')
    private val numericCharset = ('0'..'9')

    override fun nextInt() = random.nextInt()

    override fun nextInt(bound: Int) = random.nextInt(bound)

    override fun nextInt(intRange: IntRange): Int {
        val lowerBound = requireNotNull(intRange.minOrNull())
        val upperBound = requireNotNull(intRange.maxOrNull())

        return nextInt(lowerBound, upperBound)
    }

    override fun nextInt(min: Int, max: Int) = random.nextInt(max - min + 1) + min

    override fun <T> randomValue(list: List<T>) = list[nextInt(list.size)]

    override fun <T> randomValue(array: Array<T>) = array[nextInt(array.size)]

    override fun nextLetter(upper: Boolean): Char {
        val source = if (upper) alphabeticUpperCharset else alphabeticLowerCharset
        return source.random(config.random.asKotlinRandom())
    }

    override fun randomString(length: Int, numericalChars: Boolean): String {
        if (length < 1) return ""
        val charset = if (numericalChars) {
            alphabeticLowerCharset + alphabeticUpperCharset + numericCharset
        } else alphabeticLowerCharset + alphabeticUpperCharset
        return (1..length)
            .map { charset.random(this.random.asKotlinRandom()) }
            .joinToString("")
    }

    override fun nextBoolean() = random.nextBoolean()

    override fun nextLong() = random.nextLong()

    override fun nextLong(bound: Long): Long {
        return if (bound > 0) {
            var value: Long

            do {
                val bits = (nextLong().shl(1)).shr(1)
                value = bits % bound
            } while (bits - value + (bound - 1) < 0L)
            value
        } else throw IllegalArgumentException("Bound bound must be greater than 0")
    }

    override fun nextLong(longRange: LongRange): Long {
        val lowerBound = requireNotNull(longRange.minOrNull())
        val upperBound = requireNotNull(longRange.maxOrNull())

        return nextLong(lowerBound, upperBound)
    }

    override fun nextLong(min: Long, max: Long) = nextLong(max - min + 1) + min

    override fun nextFloat() = random.nextFloat()

    override fun nextDouble() = random.nextDouble()

    override fun nextChar() = nextInt().toChar()

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
    ): String = randomString(length, locale, auxiliaryChars, numericalChars)

    override fun randomString(
        length: Int,
        locale: Locale,
        indexChars: Boolean,
        auxiliaryChars: Boolean,
        punctuationChars: Boolean,
        numericalChars: Boolean,
    ): String {
        if (length < 1) return "" // base case

        val localeData = LocaleData.getInstance(ULocale.forLocale(locale))
        val mainChars = localeData.getExemplarSet(UnicodeSet.MIN_VALUE, LocaleData.ES_STANDARD)
            .ranges()
            .flatMap { (it.codepoint..it.codepointEnd).map { code -> Char(code) } }
        val auxChars = if (auxiliaryChars) {
            localeData.getExemplarSet(UnicodeSet.MIN_VALUE, LocaleData.ES_AUXILIARY)
                .ranges()
                .flatMap { (it.codepoint..it.codepointEnd).map { code -> Char(code) } }
        } else emptyList()
        val idxChars = if (indexChars) {
            localeData.getExemplarSet(UnicodeSet.MIN_VALUE, LocaleData.ES_INDEX)
                .ranges()
                .flatMap { (it.codepoint..it.codepointEnd).map { code -> Char(code) } }
        } else emptyList()
        val punctChars = if (punctuationChars) {
            localeData.getExemplarSet(UnicodeSet.MIN_VALUE, LocaleData.ES_PUNCTUATION)
                .ranges()
                .flatMap { (it.codepoint..it.codepointEnd).map { code -> Char(code) } }
        } else emptyList()
        val numChars = if (numericalChars) numericCharset else emptyList()
        val chars = (mainChars + auxChars + idxChars + punctChars + numChars)
        return List(length) { chars.random(random.asKotlinRandom()) }.joinToString("")
    }

    override fun randomString(
        min: Int,
        max: Int,
        locale: Locale,
        indexChars: Boolean,
        auxiliaryChars: Boolean,
        punctuationChars: Boolean,
        numericalChars: Boolean,
    ): String {
        val len = nextInt(min, max)
        return randomString(
            len,
            locale,
            indexChars = indexChars,
            auxiliaryChars = auxiliaryChars,
            punctuationChars = punctuationChars,
            numericalChars = numericalChars,
        )
    }

    /**
     * Returns a randomly selected enum entry of type [E].
     */
    inline fun <reified E : Enum<E>> nextEnum(): E {
        val x: Int = nextInt(enumValues<E>().size)
        return enumValues<E>()[x]
    }

    override fun <E : Enum<E>> nextEnum(enum: Class<E>): E {
        return randomValue(enum.enumConstants)
    }

    override fun <E : Enum<E>> nextEnum(values: Array<E>): E {
        return randomValue(values)
    }

    override tailrec fun <E : Enum<E>> nextEnum(enum: Class<E>, predicate: (E) -> Boolean): E {
        val enumClass = nextEnum(enum)
        return if (predicate(enumClass)) enumClass else nextEnum(enum, predicate)
    }

    /**
     * Returns a randomly selected enum entry of type [E] excluding a particular enum class by its name.
     */
    inline fun <reified E : Enum<E>> nextEnum(excludeName: String): E {
        do {
            val e: E = nextEnum()
            if (e.name.lowercase() != excludeName.lowercase()) {
                return e
            }
        } while (true)
    }

    override fun nextUUID(): String {
        val randomBytes = ByteArray(16)
        random.nextBytes(randomBytes)
        randomBytes[6] = randomBytes[6] and 0x0f // clear version
        randomBytes[6] = randomBytes[6] or 0x40 // set to version
        randomBytes[8] = randomBytes[8] and 0x3f // clear variant
        randomBytes[8] = randomBytes[8] or 0x80.toByte() // set to IETF variant
        return UUID.nameUUIDFromBytes(randomBytes).toString()
    }

    override fun <T> randomSublist(list: List<T>, size: Int, shuffled: Boolean): List<T> {
        val (from, to) = list.randomFromToIndices(size)
        return list.ifEmpty { emptyList() }
            .let { if (shuffled) it.shuffled(random) else it }
            .subList(from, to)
    }

    override fun <T> randomSublist(list: List<T>, sizeRange: IntRange, shuffled: Boolean): List<T> {
        return randomSublist(list, nextInt(sizeRange), shuffled)
    }

    override fun <T> randomSubset(set: Set<T>, size: Int, shuffled: Boolean): Set<T> {
        val (from, to) = set.randomFromToIndices(size)
        return set.ifEmpty { emptyList() }
            .let { if (shuffled) it.shuffled(random) else it }
            .mapIndexedNotNull { i, v -> if (i in from until to) v else null }
            .toSet()
    }

    override fun <T> randomSubset(set: Set<T>, sizeRange: IntRange, shuffled: Boolean): Set<T> {
        return randomSubset(set, nextInt(sizeRange), shuffled)
    }

    override fun randomPastDate(): OffsetDateTime {
        return randomPastDate(Instant.ofEpochSecond(0))
    }

    override fun randomPastDate(min: Instant): OffsetDateTime {
        val now = Instant.now()
        require(min.isBefore(now))

        return randomDate(min, now.minusMillis(1), ZoneOffset.UTC)
    }

    override fun randomFutureDate(): OffsetDateTime {
        val maxInstant = Instant.now().plus(Duration.ofDays(50 * 365))
        return randomFutureDate(maxInstant)
    }

    override fun randomFutureDate(max: Instant): OffsetDateTime {
        val now = Instant.now()
        require(max.isAfter(now))

        return randomDate(now.plusMillis(1), max, ZoneOffset.UTC)
    }

    override fun randomDate(min: Instant, max: Instant, zoneOffset: ZoneOffset): OffsetDateTime {
        val randomSeconds = nextLong(min.epochSecond, max.epochSecond)
        val randomInstant = Instant.ofEpochSecond(randomSeconds)

        return OffsetDateTime.ofInstant(randomInstant, zoneOffset)
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
