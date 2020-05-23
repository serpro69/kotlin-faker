package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.GAME_OF_THRONES] category.
 */
@Suppress("unused")
class GameOfThrones internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<GameOfThrones>(fakerService) {
    override val categoryName = CategoryName.GAME_OF_THRONES
    override val localUniqueDataProvider = LocalUniqueDataProvider<GameOfThrones>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun characters() = resolve("characters")
    fun houses() = resolve("houses")
    fun cities() = resolve("cities")
    fun quotes() = resolve("quotes")
    fun dragons() = resolve("dragons")
}
