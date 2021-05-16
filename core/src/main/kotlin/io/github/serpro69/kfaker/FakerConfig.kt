@file:JvmName("FakerConfigBuilder")

package io.github.serpro69.kfaker

import java.util.*

class FakerConfig private constructor(
    val locale: String,
    val random: Random,
    val uniqueGeneratorRetryLimit: Int
) {

    private constructor(
        locale: String,
        seed: Long,
        uniqueGeneratorRetryLimit: Int
    ) : this(locale, Random(seed), uniqueGeneratorRetryLimit)

    companion object {
        @JvmStatic
        fun builder() = Builder()
    }

    /**
     * @property locale Sets the [FakerConfig.locale] when creating an instance of [FakerConfig] with this [Builder].
     * Default: `"en"`.
     *
     * @property random Sets the [FakerConfig.random] when creating an instance of [FakerConfig] with this [Builder].
     * Default: `"en"`.
     *
     * @property randomSeed Sets the seed for [FakerConfig.random].
     * If specified, the [random] property will be ignored
     * when creating an instance of [FakerConfig] with this [Builder].
     * Default: `null`.
     *
     * @property uniqueGeneratorRetryLimit Sets the [FakerConfig.uniqueGeneratorRetryLimit]
     * when creating an instance of [FakerConfig] with this [Builder].
     * Default: `100`.
     */
    @FakerDsl
    class Builder internal constructor() {
        var locale = "en"
        var random = Random()
        var randomSeed: Long? = null
        var uniqueGeneratorRetryLimit = 100

        internal fun build() = randomSeed?.let {
            FakerConfig(locale, it, uniqueGeneratorRetryLimit)
        } ?: FakerConfig(locale, random, uniqueGeneratorRetryLimit)
    }
}

fun FakerConfig.Builder.create(block: FakerConfig.Builder.() -> Unit) = this.apply(block).build()

fun fakerConfig(block: FakerConfig.Builder.() -> Unit) = FakerConfig.Builder().apply(block).build()
