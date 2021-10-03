@file:JvmName("FakerConfigBuilder")

package io.github.serpro69.kfaker

import java.util.*

/**
 * Configuration for [Faker].
 */
class FakerConfig private constructor(
    val locale: String,
    val random: Random,
    val uniqueGeneratorRetryLimit: Int
) {

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

        fun withLocale(locale: String): Builder {
            this.locale = locale
            return this
        }

        fun withRandom(random: Random): Builder {
            this.random = random
            return this
        }

        fun withRandomSeed(seed: Long): Builder {
            this.randomSeed = seed
            return this
        }

        fun withUniqueGeneratorRetryLimit(retryLimit: Int): Builder {
            this.uniqueGeneratorRetryLimit = retryLimit
            return this
        }

        fun build() = randomSeed?.let {
            FakerConfig(locale, Random(it), uniqueGeneratorRetryLimit)
        } ?: FakerConfig(locale, random, uniqueGeneratorRetryLimit)
    }
}

@Deprecated(
    message = "This function is deprecated and will be removed in 1.9.0",
    ReplaceWith("fakerConfig { }"),
    level = DeprecationLevel.WARNING
)
fun FakerConfig.Builder.create(block: FakerConfig.Builder.() -> Unit) = this.apply(block).build()

/**
 * Creates an instance of [FakerConfig] with the config properties that were passed to the function [block].
 */
fun fakerConfig(block: FakerConfig.Builder.() -> Unit) = FakerConfig.Builder().apply(block).build()
