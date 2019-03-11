package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

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
