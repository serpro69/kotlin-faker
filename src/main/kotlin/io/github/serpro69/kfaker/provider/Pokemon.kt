package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.GAMES] category.
 */
@Suppress("unused")
class Pokemon internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.GAMES

    val names = resolve { fakerService.resolve(it, "pokemon", "names") }
    val locations = resolve { fakerService.resolve(it, "pokemon", "locations") }
    val moves = resolve { fakerService.resolve(it, "pokemon", "moves") }
}
