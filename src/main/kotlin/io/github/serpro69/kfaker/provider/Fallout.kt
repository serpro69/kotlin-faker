package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.GAMES] category.
 */
class Fallout internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.GAMES

    val characters = resolve { fakerService.resolve(Faker, it, "fallout", "characters") }
    val factions = resolve { fakerService.resolve(Faker, it, "fallout", "factions") }
    val locations = resolve { fakerService.resolve(Faker, it, "fallout", "locations") }
    val quotes = resolve { fakerService.resolve(Faker, it, "fallout", "quotes") }
}
