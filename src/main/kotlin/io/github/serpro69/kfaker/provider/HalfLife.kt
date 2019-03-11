package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.GAMES] category.
 */
class HalfLife internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.GAMES

    val character = resolve { fakerService.resolve(Faker, it, "half-life", "character") }
    val enemy = resolve { fakerService.resolve(Faker, it, "half-life", "enemy") }
    val location = resolve { fakerService.resolve(Faker, it, "half-life", "location") }
}
