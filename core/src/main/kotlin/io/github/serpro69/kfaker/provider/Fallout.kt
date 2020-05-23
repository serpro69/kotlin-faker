package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.GAMES] category.
 */
@Suppress("unused")
class Fallout internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Fallout>(fakerService) {
    override val categoryName = CategoryName.GAMES
    override val localUniqueDataProvider = LocalUniqueDataProvider<Fallout>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun characters() = resolve("fallout", "characters")
    fun factions() = resolve("fallout", "factions")
    fun locations() = resolve("fallout", "locations")
    fun quotes() = resolve("fallout", "quotes")
}
