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
 * faker.unique.enable(faker::address) // enables unique generation for all functions of Address provider
 * ```
 */
@Suppress("UNCHECKED_CAST", "unused")
class GlobalUniqueDataDataProvider internal constructor() : UniqueDataProvider() {

    @JvmSynthetic
    @PublishedApi
    override val config = UniqueProviderConfiguration()

//    /**
//     * A Set of [FakeDataProvider]s' [KClass]es that are configured to return unique values.
//     */
//    @PublishedApi
//    @JvmSynthetic
//    override val markedUnique = mutableSetOf<KClass<out FakeDataProvider>>()
//
//    /**
//     * A HashMap where the key is a [KClass] of [FakeDataProvider],
//     * and values are Maps of provider's functionName to a set of already returned (used) values.
//     */
//    @PublishedApi
//    @JvmSynthetic
//    override val usedValues = hashMapOf<KClass<out FakeDataProvider>, MutableMap<String, MutableSet<String>>>()
//
//    /**
//     * A map of key=value pairs, where
//     * A set of patterns which are matched against resolved values (before they are returned to the client)
//     * to determine if the value should be returned or not.
//     */
//    @PublishedApi
//    @JvmSynthetic
//    internal val exclusionPatterns = hashMapOf<KClass<out FakeDataProvider>, MutableMap<String, MutableSet<Regex>>>()

    /**
     * Disables "unique generation" for all providers that were configured to return unique values,
     * and clears out any already returned values, so they can possibly be returned again.
     */
    override fun disableAll() {
        config.markedUnique.clear()
        config.usedValues.clear()
        config.exclusionPatterns.clear()
    }

    /**
     * Clears the already returned (used) unique values and exclusion patterns so that values can again be returned.
     */
    override fun clearAll() {
        config.usedValues.keys.forEach { k -> config.usedValues[k] = hashMapOf() }
        config.exclusionPatterns.keys.forEach { k -> config.exclusionPatterns[k] = hashMapOf() }
    }

    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "This functionality is deprecated and will be removed in release 1.7.0",
        replaceWith = ReplaceWith("faker.unique.configuration { this.exclude<T>(funcName, values) }")
    )
    inline fun <reified T : FakeDataProvider> exclude(funcName: String, values: List<String>) {
        exclude<T>(funcName, *values.toTypedArray())
    }

    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "This functionality is deprecated and will be removed in release 1.7.0",
        replaceWith = ReplaceWith("faker.unique.configuration { this.exclude<T>(funcName, values) }")
    )
    inline fun <reified T : FakeDataProvider> exclude(funcName: String, vararg values: String) {
        if (config.markedUnique.contains(T::class)) {
            config.usedValues[T::class]?.merge(funcName, values.toMutableSet()) { oldSet, newSet ->
                oldSet.apply { addAll(newSet) }
            }
        }
    }

    // TODO remove - unused
//    inline fun <reified T : FakeDataProvider> exclude(funcName: String, vararg patterns: Regex) {
//        if (config.markedUnique.contains(T::class)) {
//            config.exclusionPatterns[T::class]?.merge(funcName, patterns.toMutableSet()) { oldSet, newSet ->
//                oldSet.apply { addAll(newSet) }
//            }
//        }
//    }

    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "This functionality is deprecated and will be removed in release 1.7.0",
        replaceWith = ReplaceWith("faker.unique.configuration { this.enable(providerProperty) }")
    )
    fun <T : FakeDataProvider> enable(providerProperty: KProperty0<T>) {
        config.enable(providerProperty.returnType.classifier as KClass<T>)
    }

    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "This functionality is deprecated and will be removed in release 1.7.0",
        replaceWith = ReplaceWith("faker.unique.configuration { this.disable(providerProperty) }")
    )
    fun <T : FakeDataProvider> disable(providerProperty: KProperty0<T>) {
        config.disable(providerProperty.returnType.classifier as KClass<T>)
    }

    fun <T : FakeDataProvider> clear(providerProperty: KProperty0<T>) {
        config.clear(providerProperty.returnType.classifier as KClass<T>)
    }

    // TODO remove - unused
//    @PublishedApi
//    @JvmSynthetic
//    internal fun <T : FakeDataProvider> enable(provider: KClass<out T>) {
//        if (!markedUnique.contains(provider)) {
//            markedUnique.add(provider).also {
//                usedValues[provider] = hashMapOf()
//                exclusionPatterns[provider] = hashMapOf()
//            }
//        }
//    }

    // TODO remove - unused
//    private fun <T : FakeDataProvider> disable(provider: KClass<out T>) {
//        if (markedUnique.contains(provider)) {
//            markedUnique.remove(provider).also {
//                usedValues.remove(provider)
//                exclusionPatterns.remove(provider)
//            }
//        }
//    }
//
    private fun <T : FakeDataProvider> clear(provider: KClass<out T>) {
        if (config.markedUnique.contains(provider)) {
            config.usedValues[provider] = hashMapOf()
            config.exclusionPatterns[provider] = hashMapOf()
        }
    }

    /**
     * Configures `this` Unique provider.
     */
    fun configuration(function: UniqueProviderConfiguration.() -> Unit) {
        config.apply(function)
    }
}