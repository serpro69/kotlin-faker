package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.GAMES] category.
 */
class Myst internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.GAMES

    val games = resolve { fakerService.resolve(Faker, it, "myst", "games") }
    val creatures = resolve { fakerService.resolve(Faker, it, "myst", "creatures") }
    val characters = resolve { fakerService.resolve(Faker, it, "myst", "characters") }
    val ages = resolve { fakerService.resolve(Faker, it, "myst", "ages") }
    val quotes = resolve { fakerService.resolve(Faker, it, "myst", "quotes") }
}
