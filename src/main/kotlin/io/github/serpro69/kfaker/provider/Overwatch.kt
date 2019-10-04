package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.GAMES] category.
 */
@Suppress("unused")
class Overwatch internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Overwatch>(fakerService) {
    override val categoryName = CategoryName.GAMES
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val heroes = resolve { fakerService.resolve(it, "overwatch", "heroes") }
    val locations = resolve { fakerService.resolve(it, "overwatch", "locations") }
    val quotes = resolve { fakerService.resolve(it, "overwatch", "quotes") }
}
