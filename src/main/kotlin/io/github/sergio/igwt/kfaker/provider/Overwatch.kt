package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.GAMES] category.
 */
class Overwatch internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.GAMES

    val heroes = resolve { fakerService.resolve(Faker, it, "overwatch", "heroes") }
    val locations = resolve { fakerService.resolve(Faker, it, "overwatch", "locations") }
    val quotes = resolve { fakerService.resolve(Faker, it, "overwatch", "quotes") }
}
