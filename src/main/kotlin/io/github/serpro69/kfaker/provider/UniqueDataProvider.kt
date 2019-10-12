package io.github.serpro69.kfaker.provider

import kotlin.reflect.*

@Suppress("UNCHECKED_CAST", "unused")
class UniqueDataProvider {

    internal val markedUnique = mutableSetOf<KClass<out FakeDataProvider>>()
    internal val usedValues = hashMapOf<KClass<out FakeDataProvider>, MutableMap<String, MutableSet<String>>>()

    fun disableAll() {
        markedUnique.clear()
        usedValues.clear()
    }

    fun clearAll() {
        usedValues.clear()
    }

    fun <T : AbstractFakeDataProvider<*>> enable(providerProperty: KProperty0<T>) {
        enable(providerProperty.returnType.classifier as KClass<T>)
    }

    fun <T : AbstractFakeDataProvider<*>> disable(providerProperty: KProperty0<T>) {
        disable(providerProperty.returnType.classifier as KClass<T>)
    }

    fun <T : AbstractFakeDataProvider<*>> clear(providerProperty: KProperty0<T>) {
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

    private fun <T : AbstractFakeDataProvider<*>> clear(provider: KClass<out T>) {
        if (markedUnique.contains(provider)) {
            usedValues[provider] = hashMapOf()
        }
    }
}
