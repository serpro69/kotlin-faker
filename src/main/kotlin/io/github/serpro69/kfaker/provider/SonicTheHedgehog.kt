package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.GAMES] category.
 */
class SonicTheHedgehog internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.GAMES

    val zone = resolve { fakerService.resolve(Faker, it, "sonic_the_hedgehog", "zone") }
    val character = resolve { fakerService.resolve(Faker, it, "sonic_the_hedgehog", "character") }
    val game = resolve { fakerService.resolve(Faker, it, "sonic_the_hedgehog", "game") }
}
