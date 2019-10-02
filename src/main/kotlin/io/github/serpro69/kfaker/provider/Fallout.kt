package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.GAMES] category.
 */
class Fallout internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.GAMES

    val characters = resolve { fakerService.resolve(it, "fallout", "characters") }
    val factions = resolve { fakerService.resolve(it, "fallout", "factions") }
    val locations = resolve { fakerService.resolve(it, "fallout", "locations") }
    val quotes = resolve { fakerService.resolve(it, "fallout", "quotes") }
}
