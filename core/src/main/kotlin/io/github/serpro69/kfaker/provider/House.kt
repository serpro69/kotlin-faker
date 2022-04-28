package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.HOUSE] category.
 */
@Suppress("unused")
class House internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<House>(fakerService) {
    override val category = YamlCategory.HOUSE
    override val localUniqueDataProvider = LocalUniqueDataProvider<House>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun furniture() = resolve("furniture")
    fun rooms() = resolve("rooms")
}
