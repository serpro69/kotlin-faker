package io.github.serpro69.kfaker.provider.unique

abstract class UniqueDataProvider {
//    internal abstract val config: UniqueProviderConfiguration

    /**
     * Disables "unique generation" for all providers that were configured to return unique values.
     */
    abstract fun disableAll()

    /**
     * Clears the already returned (used) unique values so that they can again be returned.
     */
    abstract fun clearAll()
}
