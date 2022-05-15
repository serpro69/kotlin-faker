@file:Suppress("unused")

package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.MOUNTAIN] category.
 */
class Mountain internal constructor(fakerService: FakerService) :
    AbstractFakeDataProvider<Mountain>(fakerService) {
    override val category = YamlCategory.MOUNTAIN
    override val localUniqueDataProvider = LocalUniqueDataProvider<Mountain>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun name(): String = resolve("name")
    fun range(): String = resolve("range")
}
