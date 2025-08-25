@file:JvmName("FakerConfigBuilder")

package io.github.serpro69.kfaker

import io.github.serpro69.kfaker.provider.misc.RandomProviderConfig
import kotlin.random.Random

/** Configuration for implementations of [AbstractFaker]. */
class FakerConfig
private constructor(val locale: String, val random: Random, val uniqueGeneratorRetryLimit: Int) {
    internal var randomProviderConfig: RandomProviderConfig? = null
        private set

    private constructor(
        locale: String,
        random: Random,
        uniqueGeneratorRetryLimit: Int,
        randomProviderConfig: RandomProviderConfig?,
    ) : this(locale, random, uniqueGeneratorRetryLimit) {
        this.randomProviderConfig = randomProviderConfig
    }

    companion object {
        @JvmStatic fun builder() = Builder()
    }

    /**
     * @property locale Sets the [FakerConfig.locale] when creating an instance of [FakerConfig]
     *   with this [Builder]. Default: `"en"`.
     * @property random Sets the [FakerConfig.random] when creating an instance of [FakerConfig]
     *   with this [Builder]. Default: `"en"`.
     * @property randomSeed Sets the seed for [FakerConfig.random]. If specified, the [random]
     *   property will be ignored when creating an instance of [FakerConfig] with this [Builder].
     *   Default: `null`.
     * @property uniqueGeneratorRetryLimit Sets the [FakerConfig.uniqueGeneratorRetryLimit] when
     *   creating an instance of [FakerConfig] with this [Builder]. Default: `100`.
     */
    @FakerDsl
    class Builder internal constructor() {
        var locale = "en"
        var random: Random = Random.Default
        var randomSeed: Long? = null
        var uniqueGeneratorRetryLimit = 100
        private var randomProviderConfig: RandomProviderConfig? = null

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

        fun build() =
            randomSeed?.let {
                FakerConfig(locale, Random(it), uniqueGeneratorRetryLimit, randomProviderConfig)
            } ?: FakerConfig(locale, random, uniqueGeneratorRetryLimit, randomProviderConfig)

        fun randomClassInstance(configurator: RandomProviderConfig.() -> Unit) {
            randomProviderConfig?.apply(configurator)
                ?: run { randomProviderConfig = RandomProviderConfig().also(configurator) }
        }
    }
}

/**
 * Applies the the [block] function to [ConfigBuilder] and returns as an instance of [FakerConfig]
 * from that builder.
 */
fun fakerConfig(block: ConfigBuilder): FakerConfig = FakerConfig.Builder().apply(block).build()

/**
 * Lambda with [FakerConfig.Builder] receiver type that returns a [Unit].
 *
 * Used with DSL functions to construct an instance of [FakerConfig] by applying the results of the
 * function to the [FakerConfig.Builder].
 */
typealias ConfigBuilder = FakerConfig.Builder.() -> Unit
