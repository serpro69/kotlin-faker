package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.GAMES] category.
 */
class SonicTheHedgehog internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.GAMES

    val zone = resolve { fakerService.resolve(it, "sonic_the_hedgehog", "zone") }
    val character = resolve { fakerService.resolve(it, "sonic_the_hedgehog", "character") }
    val game = resolve { fakerService.resolve(it, "sonic_the_hedgehog", "game") }
}
