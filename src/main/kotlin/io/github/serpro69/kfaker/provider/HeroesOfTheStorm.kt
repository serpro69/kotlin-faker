package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.HEROES_OF_THE_STORM] category.
 */
@Suppress("unused")
class HeroesOfTheStorm internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<HeroesOfTheStorm>(fakerService) {
    override val categoryName = CategoryName.HEROES_OF_THE_STORM
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val battlegrounds = resolve("battlegrounds")
    val classes = resolve("classes")
    val heroes = resolve("heroes")
    val quotes = resolve("quotes")
}
