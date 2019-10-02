package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.GAMES] category.
 */
class Zelda internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.GAMES

    val games = resolve { fakerService.resolve(it, "zelda", "games") }
    val characters = resolve { fakerService.resolve(it, "zelda", "characters") }
    val locations = resolve { fakerService.resolve(it, "zelda", "locations") }
    val items = resolve { fakerService.resolve(it, "zelda", "items") }
}
