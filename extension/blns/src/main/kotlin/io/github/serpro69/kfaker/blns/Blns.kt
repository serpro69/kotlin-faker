package io.github.serpro69.kfaker.blns

import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import io.github.serpro69.kfaker.AbstractFaker
import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.FakerConfig
import io.github.serpro69.kfaker.FakerDsl
import io.github.serpro69.kfaker.fakerConfig
import io.github.serpro69.kfaker.RandomService

/**
 * A kotlin-faker extension that helps to use
 * [The Big List of Naughty Strings](https://github.com/minimaxir/big-list-of-naughty-strings)
 * for various test-related inputs.
 */
@Suppress("unused")
class Blns @JvmOverloads constructor(config: FakerConfig = fakerConfig { }) : AbstractFaker(config) {
    private val _all: (filename: String) -> List<String> = {
        val inStr = requireNotNull(javaClass.classLoader.getResourceAsStream(it))
        Mapper.readValue(inStr, jacksonTypeRef())
    }

    /**
     * @property all a list of all strings.
     */
    val all: List<String> by lazy { _all("blns.json") }

    /**
     * @property allBase64 a list of all base64-encoded strings.
     */
    val allBase64: List<String> by lazy { _all("blns.base64.json") }

    fun get(category: Category): List<String> = get(*arrayOf(category))[category]!!

    fun get(vararg category: Category): Map<Category, List<String>> {
        val inStr = requireNotNull(javaClass.classLoader.getResourceAsStream("blns_categories.json"))
        val all: Map<Category, List<String>> = Mapper.readValue(inStr, jacksonTypeRef())
        return all.filter { it.key in category }
    }

    /**
     * Returns a random string of [all] strings (or [allBase64] strings if [base64] is `true`)
     */
    fun random(base64: Boolean = false): String =
        if (base64) randomService.randomValue(allBase64) else randomService.randomValue(all)

    /**
     * Returns a portion of [all] strings (or [allBase64] strings if [base64] is `true`)
     * with pseudo-randomly generated `fromIndex` and (possibly) `toIndex` values.
     *
     * @param size the desired size of the resulting list.
     * If `size <= 0` then `toIndex` will also be randomly-generated.
     */
    fun sublist(size: Int, base64: Boolean = false): List<String> =
        if (base64) randomService.randomSublist(allBase64, size) else randomService.randomSublist(all, size)

    /**
     * Returns a portion of [all] strings (or [allBase64] strings if [base64] is `true`)
     * with pseudo-randomly generated `fromIndex` and (possibly) `toIndex` values.
     *
     * @param sizeRange the desired size range of the resulting list.
     * The `size` of the returned list is the result of calling [RandomService.nextInt] with the given [sizeRange].
     * IF `size <= 0` then `toIndex` will also be randomly-generated.
     */
    fun sublist(sizeRange: IntRange, base64: Boolean = false): List<String> =
        if (base64) randomService.randomSublist(allBase64, sizeRange) else randomService.randomSublist(all, sizeRange)

    @FakerDsl
    /**
     * DSL builder for creating instances of [Blns]
     */
    class Builder internal constructor() : AbstractFaker.Builder<Blns>() {

        /**
         * Builds an instance of [Faker] with this [config].
         */
        override fun build(): Blns = Blns(config)
    }
}

/**
 * Applies the [block] function to [Blns.Builder]
 * and returns as an instance of [Blns] from that builder.
 */
@Suppress("unused")
fun blns(block: Blns.Builder.() -> Unit): Blns = Blns.Builder().apply(block).build()
