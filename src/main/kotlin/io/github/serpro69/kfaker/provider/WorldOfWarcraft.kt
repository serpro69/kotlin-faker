package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.GAMES] category.
 */
@Suppress("unused")
class WorldOfWarcraft internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<WorldOfWarcraft>(fakerService) {
    override val categoryName = CategoryName.GAMES
    override val localUniqueDataProvider = LocalUniqueDataProvider<WorldOfWarcraft>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun hero() = resolve("world_of_warcraft", "hero")
    fun quotes() = resolve("world_of_warcraft", "quotes")
}
