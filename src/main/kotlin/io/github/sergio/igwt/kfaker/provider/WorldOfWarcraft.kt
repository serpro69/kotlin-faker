package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.GAMES] category.
 */
class WorldOfWarcraft internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.GAMES

    val hero = resolve { fakerService.resolve(Faker, it, "world_of_warcraft", "hero") }
    val quotes = resolve { fakerService.resolve(Faker, it, "world_of_warcraft", "quotes") }
}
