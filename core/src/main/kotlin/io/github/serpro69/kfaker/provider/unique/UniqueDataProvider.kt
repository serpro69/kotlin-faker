package io.github.serpro69.kfaker.provider.unique

import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.provider.AbstractFakeDataProvider
import io.github.serpro69.kfaker.provider.FakeDataProvider
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KClass
import kotlin.reflect.KFunction1
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty0
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.javaField

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
        patterns.addAll(func.invoke())
    }

    /**
     * Exclude [values] from being generated.
     * This applies to all providers that are enabled for unique value generation.
     */
    fun exclude(values: List<String>) {
        TODO("Not implemented")
    }

    /**
     * Exclude a values from being generated by [Regex] patterns.
     * This applies to all providers that are enabled for unique value generation.
     *
     * @param func a function that does not take any arguments and returns a [List] of [Regex]es
     */
    fun excludePatterns(func: () -> List<Regex>) {
        patterns.addAll(func.invoke())
    }
}

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

/**
 * Local provider for unique values.
 *
 * This provider is used in [T] implementation of [FakeDataProvider] class,
 * and controls unique generation configuration of [T]'s functions.
 *
 * Example usage:
 * ```
 * Faker().address.unique().country()
 * ```
 */
@Suppress("UNCHECKED_CAST", "unused")
class LocalUniqueDataProvider<T : FakeDataProvider> internal constructor() : UniqueDataProvider() {
    override val config: UniqueProviderConfiguration
        get() = TODO("Not yet implemented")
//    override val markedUnique: MutableSet<FakeDataProvider> = mutableSetOf()
//    override val usedValues = hashMapOf<String, MutableSet<String>>()

    val markedUnique: MutableSet<FakeDataProvider> = mutableSetOf()
    val usedValues = hashMapOf<String, MutableSet<String>>()

    /**
     * In `this` class the function works the same as [clearAll] implementation.
     */
    override fun disableAll() {
        clearAll()
    }

    override fun clearAll() {
        usedValues.keys.forEach { k ->
            usedValues[k] = mutableSetOf()
        }
    }

    /**
     * Clears the already returned (used) unique values for the function with provided [name].
     *
     * Example usage:
     * ```
     * address.unique.clear("country") // clear (reset) unique values for 'country' function
     * ```
     */
    fun clear(name: String) {
        usedValues[name] = mutableSetOf()
    }
}

/**
 * Delegate class for [LocalUniqueDataProvider] used to return local providers that generate unique values.
 *
 * @param T an implementation of [AbstractFakeDataProvider]
 *
 * @property uniqueDataProvider [LocalUniqueDataProvider] of [T] type.
 */
@Suppress("UNCHECKED_CAST")
class UniqueProviderDelegate<T : AbstractFakeDataProvider<*>>(
    private val uniqueDataProvider: LocalUniqueDataProvider<T>
) : ReadOnlyProperty<T, T> {

    override fun getValue(thisRef: T, property: KProperty<*>): T {
        return if (uniqueDataProvider.markedUnique.any { it::class == thisRef::class }) {
            uniqueDataProvider.markedUnique.first { it::class == thisRef::class } as T
        } else {
            val cls = property.returnType.classifier as KClass<T>
            val prop = cls.memberProperties.first { it.name == "localUniqueDataProvider" }
            val newRef = requireNotNull(cls.primaryConstructor?.call(thisRef.fakerService))
            prop.javaField?.let {
                it.isAccessible = true
                it.set(newRef, uniqueDataProvider)
                uniqueDataProvider.markedUnique.add(newRef)
                newRef
            } ?: throw NoSuchElementException("Unable to get java field for property $prop")
        }
    }
}
