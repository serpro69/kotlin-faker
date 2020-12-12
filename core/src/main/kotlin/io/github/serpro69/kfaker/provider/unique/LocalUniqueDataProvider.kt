package io.github.serpro69.kfaker.provider.unique

import io.github.serpro69.kfaker.provider.AbstractFakeDataProvider
import io.github.serpro69.kfaker.provider.FakeDataProvider
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.javaField

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
//    override val config: UniqueProviderConfiguration
//        get() = TODO("Not yet implemented")
//    override val markedUnique: MutableSet<FakeDataProvider> = mutableSetOf()
//    override val usedValues = hashMapOf<String, MutableSet<String>>()

    internal val markedUnique: MutableSet<FakeDataProvider> = mutableSetOf()
    internal val usedValues = hashMapOf<String, MutableSet<String>>()

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
