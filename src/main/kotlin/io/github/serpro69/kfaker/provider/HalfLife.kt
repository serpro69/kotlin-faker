package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.GAMES] category.
 */
class HalfLife internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.GAMES

    val character = resolve { fakerService.resolve(Faker, it, "half_life", "character") }
    val enemy = resolve { fakerService.resolve(Faker, it, "half_life", "enemy") }
    val location = resolve { fakerService.resolve(Faker, it, "half_life", "location") }
}
