package io.github.serpro69.kfaker

import io.github.serpro69.kfaker.provider.unique.GlobalUniqueDataProvider

abstract class AbstractFaker(internal val config: FakerConfig) {
    protected val fakerService: FakerService
        get() = FakerService(this)
    protected val randomService: RandomService
        get() = fakerService.randomService

    val unique by lazy { GlobalUniqueDataProvider() }

    @FakerDsl
    /**
     * DSL builder for creating instances of [AbstractFaker]
     */
    abstract class Builder<T : AbstractFaker> {
        /**
         * @property config faker configuration for the [AbstractFaker] instance
         * which will be created with this [AbstractFaker.Builder].
         */
        protected var config: FakerConfig = io.github.serpro69.kfaker.fakerConfig { }

        /**
         * Sets [config] configuration for this [AbstractFaker.Builder]
         * using the results of the [block] function.
         *
         * This [config] will then be used when an instance of [AbstractFaker] is created using this [AbstractFaker.Builder]
         */
        fun fakerConfig(block: ConfigBuilder) {
            config = io.github.serpro69.kfaker.fakerConfig(block)
        }

        /**
         * Builds an instance of [AbstractFaker] with this [config].
         */
        abstract fun build(): T
    }
}
