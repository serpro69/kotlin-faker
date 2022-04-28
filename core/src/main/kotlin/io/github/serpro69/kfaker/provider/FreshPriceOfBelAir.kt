package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.THE_FRESH_PRINCE_OF_BEL_AIR] category.
 */
@Suppress("unused")
class FreshPriceOfBelAir internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<FreshPriceOfBelAir>(fakerService) {
    override val category = YamlCategory.THE_FRESH_PRINCE_OF_BEL_AIR
    override val localUniqueDataProvider = LocalUniqueDataProvider<FreshPriceOfBelAir>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun characters() = resolve("characters")
    fun celebrities() = resolve("celebrities")
    fun quotes() = resolve("quotes")
}
