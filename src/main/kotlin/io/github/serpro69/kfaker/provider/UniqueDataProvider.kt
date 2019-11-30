package io.github.serpro69.kfaker.provider

import kotlin.properties.*
import kotlin.reflect.*
import kotlin.reflect.full.*
import kotlin.reflect.jvm.*

abstract class UniqueDataProvider {
    internal abstract val markedUnique: Set<*>
    internal abstract val usedValues: Map<*, *>

    abstract fun disableAll()

    abstract fun clearAll()
}

@Suppress("UNCHECKED_CAST", "unused")
class GlobalUniqueDataDataProvider : UniqueDataProvider() {
    @PublishedApi
    @JvmSynthetic
    override val markedUnique = mutableSetOf<KClass<out FakeDataProvider>>()

    @PublishedApi
    @JvmSynthetic
    override val usedValues = hashMapOf<KClass<out FakeDataProvider>, MutableMap<String, MutableSet<String>>>()

    override fun disableAll() {
        markedUnique.clear()
        usedValues.clear()
    }

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

@Suppress("UNCHECKED_CAST", "unused")
class LocalUniqueDataProvider<T : FakeDataProvider> : UniqueDataProvider() {
    override val markedUnique: MutableSet<FakeDataProvider> = mutableSetOf()
    override val usedValues = hashMapOf<String, MutableSet<String>>()

    override fun disableAll() {
        clearAll()
    }

    override fun clearAll() {
        usedValues.keys.forEach { k ->
            usedValues[k] = mutableSetOf()
        }
    }

    fun clear(name: String) {
        usedValues[name] = mutableSetOf()
    }
}

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
