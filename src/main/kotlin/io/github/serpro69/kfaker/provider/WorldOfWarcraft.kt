package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.GAMES] category.
 */
@Suppress("unused")
class WorldOfWarcraft internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.GAMES

    val hero = resolve { fakerService.resolve(it, "world_of_warcraft", "hero") }
    val quotes = resolve { fakerService.resolve(it, "world_of_warcraft", "quotes") }
}
