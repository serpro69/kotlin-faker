@file:Suppress("unused")

package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.DORAEMON] category.
 */
class Doraemon internal constructor(fakerService: FakerService) :
    AbstractFakeDataProvider<Doraemon>(fakerService) {
    override val category = YamlCategory.DORAEMON
    override val localUniqueDataProvider = LocalUniqueDataProvider<Doraemon>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun characters(): String = resolve("characters")
    fun gadgets(): String = resolve("gadgets")
    fun locations(): String = resolve("locations")
}

