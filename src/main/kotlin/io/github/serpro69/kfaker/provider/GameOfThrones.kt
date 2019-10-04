package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.GAME_OF_THRONES] category.
 */
@Suppress("unused")
class GameOfThrones internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<GameOfThrones>(fakerService) {
    override val categoryName = CategoryName.GAME_OF_THRONES
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val characters = resolve("characters")
    val houses = resolve("houses")
    val cities = resolve("cities")
    val quotes = resolve("quotes")
    val dragons = resolve("dragons")
}
