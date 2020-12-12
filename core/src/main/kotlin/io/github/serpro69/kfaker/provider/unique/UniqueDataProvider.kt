package io.github.serpro69.kfaker.provider.unique

abstract class UniqueDataProvider {
    internal abstract val config: UniqueProviderConfiguration

//    /**
//     * A set of providers that are configured to return unique values.
//     */
//    internal abstract val markedUnique: Set<*>
//
//    /**
//     * A map of key=value pairs representing already returned (used) values
//     * which will not be returned again.
//     */
//    internal abstract val usedValues: Map<*, *>

    /**
     * Disables "unique generation" for all providers that were configured to return unique values.
     */
    abstract fun disableAll()

    /**
     * Clears the already returned (used) unique values so that they can again be returned.
     */
    abstract fun clearAll()
}
