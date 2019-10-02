package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.GAME_OF_THRONES] category.
 */
@Suppress("unused")
class GameOfThrones internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.GAME_OF_THRONES

    val characters = resolve { fakerService.resolve(it, "characters") }
    val houses = resolve { fakerService.resolve(it, "houses") }
    val cities = resolve { fakerService.resolve(it, "cities") }
    val quotes = resolve { fakerService.resolve(it, "quotes") }
    val dragons = resolve { fakerService.resolve(it, "dragons") }
}
