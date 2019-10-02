package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.GAMES] category.
 */
class Myst internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.GAMES

    val games = resolve { fakerService.resolve(it, "myst", "games") }
    val creatures = resolve { fakerService.resolve(it, "myst", "creatures") }
    val characters = resolve { fakerService.resolve(it, "myst", "characters") }
    val ages = resolve { fakerService.resolve(it, "myst", "ages") }
    val quotes = resolve { fakerService.resolve(it, "myst", "quotes") }
}
