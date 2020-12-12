package io.github.serpro69.kfaker.provider.unique

import io.github.serpro69.kfaker.provider.Address
import io.github.serpro69.kfaker.provider.FakeDataProvider
import kotlin.reflect.KClass
import kotlin.reflect.KFunction1
import kotlin.reflect.KProperty0

/**
 * Provides configuration for Unique Generation of values.
 */
class UniqueProviderConfiguration @PublishedApi internal constructor() {

    /**
     * A Set of [Regex]es that are used to exclude values from being returned when unique generation is enabled.
     */
    @JvmSynthetic
    @PublishedApi
    internal val patterns = mutableSetOf<Regex>()

    /**
     * A Set of [FakeDataProvider]s' [KClass]es that are configured to return unique values.
     */
    @PublishedApi
    @JvmSynthetic
    internal val markedUnique = mutableSetOf<KClass<out FakeDataProvider>>()

    /**
     * A HashMap where the key is a [KClass] of [FakeDataProvider],
     * and values are Maps of provider's functionName to a set of already returned (used) values.
     */
    @PublishedApi
    @JvmSynthetic
    internal val usedValues = hashMapOf<KClass<out FakeDataProvider>, MutableMap<String, MutableSet<String>>>()

    /**
     * A map of key=value pairs, where
     * A set of patterns which are matched against resolved values (before they are returned to the client)
     * to determine if the value should be returned or not.
     */
    @PublishedApi
    @JvmSynthetic
    internal val exclusionPatterns = hashMapOf<KClass<out FakeDataProvider>, MutableMap<String, MutableSet<Regex>>>()

    /**
     * Disables generation of unique values for [providerFunction] of [T] provider.
     *
     * @param T an implementation class of [FakeDataProvider], for example [Address]
     * @param providerFunction a [KProperty0] of the provider function that generates a value,
     *  for example `address::country` for [Address.country]
     */
    fun <T : FakeDataProvider> disable(providerFunction: KProperty0<T>) {
        disable(providerFunction.returnType.classifier as KClass<T>)
    }

    /**
     * Disables unique generation of values for all providers.
     */
    fun disableAll() {
        markedUnique.clear()
        usedValues.clear()
        exclusionPatterns.clear()
    }

    /**
     * Enable generation of unique values for [providerFunction] of [T] provider.
     *
     * @param T an implementation class of [FakeDataProvider], for example [Address]
     * @param providerFunction a [KProperty0] of the provider function that generates a value,
     *  for example `address::country` for [Address.country]
     */
    fun <T : FakeDataProvider> enable(
        providerFunction: KProperty0<T>,
        config: UniqueProviderConfiguration.() -> Unit
    ) {
        enable(providerFunction)
        this.apply(config)
    }

    /**
     * Enable generation of unique values for [providerFunction] of [T] provider.
     *
     * @param T an implementation class of [FakeDataProvider], for example [Address]
     * @param providerFunction a [KProperty0] of the provider function that generates a value,
     *  for example `address::country` for [Address.country]
     */
    fun <T : FakeDataProvider> enable(providerFunction: KProperty0<T>) {
        enable(providerFunction.returnType.classifier as KClass<T>)
    }

    /**
     * Enable generation of unique values for [T] provider.
     *
     * @param T an implementation class of [FakeDataProvider], for example [Address]
     */
    @PublishedApi
    @JvmSynthetic
    internal fun <T : FakeDataProvider> enable(provider: KClass<out T>) {
        if (!markedUnique.contains(provider)) {
            markedUnique.add(provider).also {
                usedValues[provider] = hashMapOf()
                exclusionPatterns[provider] = hashMapOf()
            }
        }
    }

    /**
     * Disable generation of unique values for [T] provider.
     *
     * @param T an implementation class of [FakeDataProvider], for example [Address]
     */
    @PublishedApi
    @JvmSynthetic
    internal fun <T : FakeDataProvider> disable(provider: KClass<out T>) {
        if (markedUnique.contains(provider)) {
            markedUnique.remove(provider).also {
                usedValues.remove(provider)
                exclusionPatterns.remove(provider)
            }
        }
    }

    /**
     * Clears the already returned (used) unique values and exclusion patterns
     * so that values can again be returned with the [T] provider.
     *
     * @param T an implementation class of [FakeDataProvider], for example [Address]
     */
    @PublishedApi
    @JvmSynthetic
    internal fun <T : FakeDataProvider> clear(provider: KClass<out T>) {
        if (markedUnique.contains(provider)) {
            usedValues[provider] = hashMapOf()
            exclusionPatterns[provider] = hashMapOf()
        }
    }

    /**
     * Exclude [values] from being generated with function [providerFunction] in provider [T].
     *
     * @param T an implementation class of [FakeDataProvider], for example [Address]
     * @param providerFunction provider function that generates a value,
     *  for example `Address::country` for [Address.country]
     * @param values values that should not be generated when calling the [providerFunction] in provider [T]
     */
    inline fun <reified T : FakeDataProvider> exclude(providerFunction: KFunction1<T, String>, values: List<String>) {
        exclude<T>(providerFunction.name, values)
    }

    /**
     * Exclude [values] from being generated with function [funcName] in provider [T].
     *
     * @param T an implementation class of [FakeDataProvider], for example [Address]
     * @param funcName name of the function that generates a value,
     *  for example `"country"` for [Address.country]
     * @param values values that should not be generated when calling the [funcName] function in provider [T]
     */
    inline fun <reified T : FakeDataProvider> exclude(funcName: String, values: List<String>) {
        exclude<T>(funcName, *values.toTypedArray())
    }

    /**
     * Exclude [values] from being generated with function [funcName] in provider [T].
     *
     * @param T an implementation class of [FakeDataProvider], for example [Address]
     * @param funcName name of the function that generates a value,
     *  for example `"country"` for [Address.country]
     * @param values values that should not be generated when calling the [funcName] function in provider [T]
     */
    inline fun <reified T : FakeDataProvider> exclude(funcName: String, vararg values: String) {
        if (markedUnique.contains(T::class)) {
            usedValues[T::class]?.merge(funcName, values.toMutableSet()) { oldSet, newSet ->
                oldSet.apply { addAll(newSet) }
            }
        }
    }

    /**
     * Exclude a values from being generated by [Regex] patterns in provider [T].
     *
     * @param T an implementation class of [FakeDataProvider], for example [Address]
     * @param func a function that does not take any arguments and returns a [List] of [Regex]es
     */
    @JvmName("excludePatternsFor")
    inline fun <reified T : FakeDataProvider> excludePatterns(func: () -> List<Regex>) {
        TODO("Not implemented")
        patterns.addAll(func.invoke())
    }

    /**
     * Exclude a values from being generated by [Regex] patterns
     * when a [providerFunction] in provider [T] is called
     *
     * @param T an implementation class of [FakeDataProvider], for example [Address]
     * @param providerFunction provider function that generates a value,
     *  for example `Address::country` for [Address.country]
     * @param func a function that does not take any arguments and returns a [List] of [Regex]es
     */
    inline fun <reified T : FakeDataProvider> excludePatterns(
        providerFunction: KFunction1<T, String>,
        func: () -> List<Regex>
    ) {
        TODO("Not implemented")
        patterns.addAll(func.invoke())
    }

    /**
     * Exclude [values] from being generated.
     *
     * This applies to ALL providers that are enabled for unique value generation.
     */
    fun exclude(values: List<String>) {
        TODO("Not implemented")
    }

    /**
     * Exclude a values from being generated by [Regex] patterns.
     *
     * This applies to ALL providers that are enabled for unique value generation.
     *
     * @param func a function that does not take any arguments and returns a [List] of [Regex]es
     */
    fun excludePatterns(func: () -> List<Regex>) {
        TODO("Not implemented")
        patterns.addAll(func.invoke())
    }
}