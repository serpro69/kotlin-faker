package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.GAMES] category.
 */
class Witcher internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.GAMES

    val characters = resolve { fakerService.resolve(Faker, it, "witcher", "characters") }
    val witchers = resolve { fakerService.resolve(Faker, it, "witcher", "witchers") }
    val schools = resolve { fakerService.resolve(Faker, it, "witcher", "schools") }
    val locations = resolve { fakerService.resolve(Faker, it, "witcher", "locations") }
    val quotes = resolve { fakerService.resolve(Faker, it, "witcher", "quotes") }
    val monsters = resolve { fakerService.resolve(Faker, it, "witcher", "monsters") }
}
