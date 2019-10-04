package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.GAMES] category.
 */
@Suppress("unused")
class Zelda internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Zelda>(fakerService) {
    override val categoryName = CategoryName.GAMES
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    fun games() = resolve("zelda", "games")
    fun characters() = resolve("zelda", "characters")
    fun locations() = resolve("zelda", "locations")
    fun items() = resolve("zelda", "items")
}
