package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

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
