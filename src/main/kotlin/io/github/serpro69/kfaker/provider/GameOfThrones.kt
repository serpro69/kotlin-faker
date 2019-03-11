package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.GAME_OF_THRONES] category.
 */
class GameOfThrones internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.GAME_OF_THRONES

    val characters = resolve { fakerService.resolve(Faker, it, "characters") }
    val houses = resolve { fakerService.resolve(Faker, it, "houses") }
    val cities = resolve { fakerService.resolve(Faker, it, "cities") }
    val quotes = resolve { fakerService.resolve(Faker, it, "quotes") }
    val dragons = resolve { fakerService.resolve(Faker, it, "dragons") }
}
