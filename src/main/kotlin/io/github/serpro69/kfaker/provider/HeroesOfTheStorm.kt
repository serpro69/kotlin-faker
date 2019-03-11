package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.HEROES_OF_THE_STORM] category.
 */
class HeroesOfTheStorm internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.HEROES_OF_THE_STORM

    val battlegrounds = resolve { fakerService.resolve(Faker, it, "battlegrounds") }
    val classes = resolve { fakerService.resolve(Faker, it, "classes") }
    val heroes = resolve { fakerService.resolve(Faker, it, "heroes") }
    val quotes = resolve { fakerService.resolve(Faker, it, "quotes") }
}
