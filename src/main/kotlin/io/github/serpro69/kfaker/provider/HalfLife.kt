package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.GAMES] category.
 */
@Suppress("unused")
class HalfLife internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<HalfLife>(fakerService) {
    override val categoryName = CategoryName.GAMES
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val character = resolve { fakerService.resolve(it, "half_life", "character") }
    val enemy = resolve { fakerService.resolve(it, "half_life", "enemy") }
    val location = resolve { fakerService.resolve(it, "half_life", "location") }
}
