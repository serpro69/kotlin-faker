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
     *
     * This applies to ALL providers that have unique generation enabled.
     */
    @JvmSynthetic
    @PublishedApi
    internal val excludedPatterns = mutableListOf<Regex>()

    /**
     * A List of [String]s used to exclude values from being returned when unique generation is enabled.
     *
     * This applies to ALL providers that have unique generation enabled.
     */
    @PublishedApi
    @JvmSynthetic
    internal val excludedValues = mutableListOf<String>()

    /**
     * A Set of [FakeDataProvider]s' [KClass]es that are configured to return unique values.
     */
    @PublishedApi
    @JvmSynthetic
    internal val markedUnique = mutableSetOf<KClass<out FakeDataProvider>>()

    /**
     * A HashMap where the key is a [KClass] of [FakeDataProvider],
     * and value is a set of already returned (used) values in this provider.
     *
     * This applies to ALL functions in a particular provider.
     */
    @PublishedApi
    @JvmSynthetic
    internal val usedProviderValues = hashMapOf<KClass<out FakeDataProvider>, MutableSet<String>>()

    /**
     * A HashMap where the key is a [KClass] of [FakeDataProvider],
     * and values are Maps of provider's functionName to a set of already returned (used) values.
     */
    @PublishedApi
    @JvmSynthetic
    internal val usedProviderFunctionValues = hashMapOf<KClass<out FakeDataProvider>, MutableMap<String, MutableSet<String>>>()

    /**
     * A map of key=value pairs, where
     * A set of patterns which are matched against resolved values (before they are returned to the client)
     * to determine if the value should be returned or not.
     */
    @PublishedApi
    @JvmSynthetic
    internal val providerExclusionPatterns = hashMapOf<KClass<out FakeDataProvider>, MutableMap<String, MutableSet<Regex>>>()

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
        usedProviderFunctionValues.clear()
        excludedValues.clear()
        excludedPatterns.clear()
        providerExclusionPatterns.clear()
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
        apply(config)
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
                usedProviderValues[provider] = mutableSetOf()
                usedProviderFunctionValues[provider] = hashMapOf()
                providerExclusionPatterns[provider] = hashMapOf()
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
                usedProviderFunctionValues.remove(provider)
                providerExclusionPatterns.remove(provider)
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
            usedProviderFunctionValues[provider] = hashMapOf()
            providerExclusionPatterns[provider] = hashMapOf()
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
    inline fun <reified T : FakeDataProvider> excludeForFunction(providerFunction: KFunction1<T, String>, values: List<String>) {
        excludeForFunction<T>(providerFunction.name, values)
    }

    /**
     * Exclude [values] from being generated with function [funcName] in provider [T].
     *
     * @param T an implementation class of [FakeDataProvider], for example [Address]
     * @param funcName name of the function that generates a value,
     *  for example `"country"` for [Address.country]
     * @param values values that should not be generated when calling the [funcName] function in provider [T]
     */
    inline fun <reified T : FakeDataProvider> excludeForFunction(funcName: String, values: List<String>) {
        excludeForFunction<T>(funcName, *values.toTypedArray())
    }

    /**
     * Exclude [values] from being generated with function [funcName] in provider [T].
     *
     * @param T an implementation class of [FakeDataProvider], for example [Address]
     * @param funcName name of the function that generates a value,
     *  for example `"country"` for [Address.country]
     * @param values values that should not be generated when calling the [funcName] function in provider [T]
     */
    inline fun <reified T : FakeDataProvider> excludeForFunction(funcName: String, vararg values: String) {
        if (markedUnique.contains(T::class)) {
            usedProviderFunctionValues[T::class]?.merge(funcName, values.toMutableSet()) { oldSet, newSet ->
                oldSet.apply { addAll(newSet) }
            }
        }
    }

    /**
     * Exclude [values] from being generated with in provider [T].
     *
     * @param T an implementation class of [FakeDataProvider], for example [Address]
     * @param values values that should not be generated when calling any of the functions in provider [T]
     */
    inline fun <reified T : FakeDataProvider> excludeForProvider(values: List<String>) {
        excludeForProvider<T>(*values.toTypedArray())
    }

    /**
     * Exclude [values] from being generated with in provider [T].
     *
     * @param T an implementation class of [FakeDataProvider], for example [Address]
     * @param values values that should not be generated when calling any of the functions in provider [T]
     */
    inline fun <reified T : FakeDataProvider> excludeForProvider(vararg values: String) {
        if (markedUnique.contains(T::class)) {
            usedProviderValues[T::class]?.addAll(values.toList())
        }
    }

    /**
     * Exclude a values from being generated by [Regex] patterns in provider [T].
     *
     * @param T an implementation class of [FakeDataProvider], for example [Address]
     * @param func a function that does not take any arguments and returns a [List] of [Regex]es
     */
    inline fun <reified T : FakeDataProvider> excludeFor(func: () -> List<Regex>) {
        TODO("Not implemented")
        excludedPatterns.addAll(func.invoke())
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
    inline fun <reified T : FakeDataProvider> excludeFor(
        providerFunction: KFunction1<T, String>,
        func: () -> List<Regex>
    ) {
        TODO("Not implemented")
        excludedPatterns.addAll(func.invoke())
    }

    /**
     * Exclude [values] from being generated.
     *
     * This applies to ALL providers that are enabled for unique value generation.
     */
    fun exclude(values: List<String>) {
        excludedValues.addAll(values)
    }

    /**
     * Exclude a values from being generated by [Regex] patterns.
     *
     * This applies to ALL providers that are enabled for unique value generation.
     *
     * @param func a function that does not take any arguments and returns a [List] of [Regex]es
     */
    fun exclude(func: () -> List<Regex>) {
        excludedPatterns.addAll(func.invoke())
    }
}