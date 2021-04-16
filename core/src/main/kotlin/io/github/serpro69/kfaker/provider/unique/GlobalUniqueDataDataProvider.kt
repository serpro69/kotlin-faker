package io.github.serpro69.kfaker.provider.unique

import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.Faker
import kotlin.reflect.KClass
import kotlin.reflect.KProperty0

/**
 * Global provider for unique values.
 *
 * This provider is used in [Faker] class to control global unique generation configuration of faker providers.
 *
 * Example usage:
 * ```
 * val faker = Faker()
 * faker.unique.configuration {
 *     enable(faker::address) // enables unique generation for all functions of Address provider
 *     enable(faker::name) // enables unique generation for all functions of Name provider
 *     exclude(listOfValues) // exclude values from `listOfValue` collection from being generated (for all providers that are enabled for unique generation)
 *     enable(faker::internet) { enables unique generation for all functions of Internet provider
 *         exclude<Internet>(listOfValues) // exclude values from `listOfValue` collection from being generated with Internet provider
 *         exclude(faker::internet, listOfPatterns)
 *     }
 * }
 * ```
 */
@Suppress("UNCHECKED_CAST")
class GlobalUniqueDataDataProvider internal constructor() : UniqueDataProvider() {

    @JvmSynthetic
    @PublishedApi
    internal val config = UniqueProviderConfiguration()

    /**
     * Disables "unique generation" for all providers that were configured to return unique values,
     * and clears out any already returned values, so they can possibly be returned again.
     */
    override fun disableAll() {
        config.disableAll()
    }

    /**
     * Clears the already returned (used) unique values and exclusion patterns so that values can again be returned.
     */
    override fun clearAll() {
        config.usedProviderFunctionValues.keys.forEach { k -> config.usedProviderFunctionValues[k] = hashMapOf() }
        config.providerFunctionExclusionPatterns.keys.forEach { k -> config.providerFunctionExclusionPatterns[k] = hashMapOf() }
    }

    fun <T : FakeDataProvider> clear(providerProperty: KProperty0<T>) {
        config.clear(providerProperty.returnType.classifier as KClass<T>)
    }

    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "This functionality is deprecated and will be removed in release 1.8.0",
        replaceWith = ReplaceWith("faker.unique.configuration { this.exclude<T>(funcName, values) }")
    )
    inline fun <reified T : FakeDataProvider> exclude(funcName: String, values: List<String>) {
        exclude<T>(funcName, *values.toTypedArray())
    }

    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "This functionality is deprecated and will be removed in release 1.8.0",
        replaceWith = ReplaceWith("faker.unique.configuration { this.exclude<T>(funcName, values) }")
    )
    inline fun <reified T : FakeDataProvider> exclude(funcName: String, vararg values: String) {
        if (config.markedUnique.contains(T::class)) {
            config.usedProviderFunctionValues[T::class]?.merge(funcName, values.toMutableSet()) { oldSet, newSet ->
                oldSet.apply { addAll(newSet) }
            }
        }
    }

    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "This functionality is deprecated and will be removed in release 1.8.0",
        replaceWith = ReplaceWith("faker.unique.configuration { this.enable(providerProperty) }")
    )
    fun <T : FakeDataProvider> enable(providerProperty: KProperty0<T>) {
        config.enable(providerProperty.returnType.classifier as KClass<T>)
    }

    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "This functionality is deprecated and will be removed in release 1.8.0",
        replaceWith = ReplaceWith("faker.unique.configuration { this.disable(providerProperty) }")
    )
    fun <T : FakeDataProvider> disable(providerProperty: KProperty0<T>) {
        config.disable(providerProperty.returnType.classifier as KClass<T>)
    }

    /**
     * Configures `this` Unique provider.
     */
    fun configuration(function: UniqueProviderConfiguration.() -> Unit) {
        config.apply(function)
    }
}