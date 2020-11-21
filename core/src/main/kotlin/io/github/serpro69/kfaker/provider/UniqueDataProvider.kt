package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.Faker
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty0
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.javaField

abstract class UniqueDataProvider {
    /**
     * A set of providers that are configured to return unique values.
     */
    internal abstract val markedUnique: Set<*>

    /**
     * A map of key=value pairs representing already returned (used) values
     * which will not be returned again.
     */
    internal abstract val usedValues: Map<*, *>

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
class GlobalUniqueDataDataProvider : UniqueDataProvider() {
    /**
     * A Set of [FakeDataProvider]s' [KClass]es that are configured to return unique values.
     */
    @PublishedApi
    @JvmSynthetic
    override val markedUnique = mutableSetOf<KClass<out FakeDataProvider>>()

    /**
     * A HashMap where the key is a [KClass] of [FakeDataProvider],
     * and values are Maps of provider's functionName to a set of already returned (used) values.
     */
    @PublishedApi
    @JvmSynthetic
    override val usedValues = hashMapOf<KClass<out FakeDataProvider>, MutableMap<String, MutableSet<String>>>()

    /**
     * Disables "unique generation" for all providers that were configured to return unique values,
     * and clears out any already returned values, so they can possibly be returned again.
     */
    override fun disableAll() {
        markedUnique.clear()
        usedValues.clear()
    }

    /**
     * Clears the already returned (used) unique values so that they can again be returned.
     */
    override fun clearAll() {
        usedValues.keys.forEach { k ->
            usedValues[k] = hashMapOf()
        }
    }

    inline fun <reified T : FakeDataProvider> exclude(funcName: String, values: List<String>) {
        exclude<T>(funcName, *values.toTypedArray())
    }

    inline fun <reified T : FakeDataProvider> exclude(funcName: String, vararg values: String) {
        if (markedUnique.contains(T::class)) {
            usedValues[T::class]?.merge(funcName, values.toMutableSet()) { oldSet, newSet ->
                oldSet.apply { addAll(newSet) }
            }
        }
    }

    fun <T : FakeDataProvider> enable(providerProperty: KProperty0<T>) {
        enable(providerProperty.returnType.classifier as KClass<T>)
    }

    fun <T : FakeDataProvider> disable(providerProperty: KProperty0<T>) {
        disable(providerProperty.returnType.classifier as KClass<T>)
    }

    fun <T : FakeDataProvider> clear(providerProperty: KProperty0<T>) {
        clear(providerProperty.returnType.classifier as KClass<T>)
    }

    @PublishedApi
    @JvmSynthetic
    internal fun <T : FakeDataProvider> enable(provider: KClass<out T>) {
        if (!markedUnique.contains(provider)) {
            markedUnique.add(provider).also {
                usedValues[provider] = hashMapOf()
            }
        }
    }

    private fun <T : FakeDataProvider> disable(provider: KClass<out T>) {
        if (markedUnique.contains(provider)) {
            markedUnique.remove(provider).also {
                usedValues.remove(provider)
            }
        }
    }

    private fun <T : FakeDataProvider> clear(provider: KClass<out T>) {
        if (markedUnique.contains(provider)) {
            usedValues[provider] = hashMapOf()
        }
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
class LocalUniqueDataProvider<T : FakeDataProvider> : UniqueDataProvider() {
    override val markedUnique: MutableSet<FakeDataProvider> = mutableSetOf()
    override val usedValues = hashMapOf<String, MutableSet<String>>()

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
