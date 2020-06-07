package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.GAMES] category.
 */
@Suppress("unused")
class WarhammerFantasy internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<WarhammerFantasy>(fakerService) {
    override val categoryName = CategoryName.GAMES
    override val localUniqueDataProvider = LocalUniqueDataProvider<WarhammerFantasy>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun heroes() = resolve("warhammer_fantasy", "heros")
    fun quotes() = resolve("warhammer_fantasy", "quotes")
    fun locations() = resolve("warhammer_fantasy", "locations")
    fun factions() = resolve("warhammer_fantasy", "factions")
    fun creatures() = resolve("warhammer_fantasy", "creatures")
}
