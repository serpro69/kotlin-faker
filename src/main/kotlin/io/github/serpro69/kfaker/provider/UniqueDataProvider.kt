package io.github.serpro69.kfaker.provider

import kotlin.properties.*
import kotlin.reflect.*

class UniqueDataProvider<T : FakeDataProvider> {

    val markedUnique = mutableSetOf<AbstractFakeDataProvider<T>>()
    val usedValues = mutableMapOf<String, MutableSet<String>>()
}

class UniqueProviderDelegate<T : AbstractFakeDataProvider<T>> internal constructor(
    uniqueProvider: UniqueDataProvider<T>
) : ReadOnlyProperty<T, T> {
    private val delegate by lazy { uniqueProvider }

    override fun getValue(thisRef: T, property: KProperty<*>): T {
        if (!delegate.markedUnique.contains(thisRef)) {
            delegate.markedUnique.add(thisRef)
        }
        return thisRef
    }
}
