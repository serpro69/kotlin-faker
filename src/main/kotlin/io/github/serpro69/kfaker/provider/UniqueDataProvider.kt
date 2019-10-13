package io.github.serpro69.kfaker.provider

import kotlin.properties.*
import kotlin.reflect.*
import kotlin.reflect.full.*

interface UniqueProvider {

    fun disableAll()
    fun clearAll()
    fun <T : FakeDataProvider> enable(providerProperty: KProperty0<T>)
    fun <T : FakeDataProvider> disable(providerProperty: KProperty0<T>)
    fun <T : FakeDataProvider> clear(providerProperty: KProperty0<T>)
}

@Suppress("UNCHECKED_CAST", "unused")
class GlobalUniqueDataProvider : UniqueProvider {
    internal val markedUnique = mutableSetOf<KClass<out FakeDataProvider>>()
    internal val usedValues = hashMapOf<KClass<out FakeDataProvider>, MutableMap<String, MutableSet<String>>>()

    override fun disableAll() {
        markedUnique.clear()
        usedValues.clear()
    }

    override fun clearAll() {
        usedValues.clear()
    }

    override fun <T : FakeDataProvider> enable(providerProperty: KProperty0<T>) {
        enable(providerProperty.returnType.classifier as KClass<T>)
    }

    override fun <T : FakeDataProvider> disable(providerProperty: KProperty0<T>) {
        disable(providerProperty.returnType.classifier as KClass<T>)
    }

    override fun <T : FakeDataProvider> clear(providerProperty: KProperty0<T>) {
        clear(providerProperty.returnType.classifier as KClass<T>)
    }

    private fun <T : FakeDataProvider> enable(provider: KClass<out T>) {
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
class UniqueDataProvider<T : FakeDataProvider> : UniqueProvider {

    internal val markedUnique: MutableSet<FakeDataProvider> = mutableSetOf()
    internal val usedValues = hashMapOf<String, MutableSet<String>>()

    override fun disableAll() {
        markedUnique.clear()
        usedValues.clear()
    }

    override fun clearAll() {
        usedValues.clear()
    }

    override fun <T : FakeDataProvider> enable(providerProperty: KProperty0<T>) {
        enable(providerProperty.invoke())
    }

    override fun <T : FakeDataProvider> disable(providerProperty: KProperty0<T>) {
        disable(providerProperty.invoke())
    }

    override fun <T : FakeDataProvider> clear(providerProperty: KProperty0<T>) {
        clear(providerProperty.invoke())
    }

    private fun <T : FakeDataProvider> enable(provider: T) {
        TODO("not implemented")
//        if (!markedUnique.contains(provider)) {
//            markedUnique.add(provider).also {
//                usedValues[provider::class] = hashMapOf()
//            }
//        }
    }

    private fun <T : FakeDataProvider> disable(provider: T) {
        TODO("not implemented")
//        if (markedUnique.contains(provider)) {
//            markedUnique.remove(provider).also {
//                usedValues.remove(provider::class)
//            }
//        }
    }

    private fun <T : FakeDataProvider> clear(provider: T) {
        TODO("not implemented")
//        if (markedUnique.contains(provider)) {
//            usedValues[provider::class] = hashMapOf()
//        }
    }
}

class UniqueProviderDelegate<T : AbstractFakeDataProvider<*>>(
    private val uniqueDataProvider: UniqueDataProvider<T>
) : ReadOnlyProperty<T, T> {

    override fun getValue(thisRef: T, property: KProperty<*>): T {
        return if (uniqueDataProvider.markedUnique.any { it::class == thisRef::class }) {
            uniqueDataProvider.markedUnique.first { it::class == thisRef::class } as T
        } else {
            val cls = property.returnType.classifier as KClass<T>
            val newRef = requireNotNull(cls.primaryConstructor?.call(thisRef.fakerService))
            uniqueDataProvider.markedUnique.add(newRef)
            newRef
        }
    }
}
