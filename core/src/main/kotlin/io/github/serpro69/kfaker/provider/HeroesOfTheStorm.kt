package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.HEROES_OF_THE_STORM] category.
 */
@Suppress("unused")
class HeroesOfTheStorm internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<HeroesOfTheStorm>(fakerService) {
    override val categoryName = CategoryName.HEROES_OF_THE_STORM
    override val localUniqueDataProvider = LocalUniqueDataProvider<HeroesOfTheStorm>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun battlegrounds() = resolve("battlegrounds")
    fun classes() = resolve("classes")
    fun heroes() = resolve("heroes")
    fun quotes() = resolve("quotes")
}
