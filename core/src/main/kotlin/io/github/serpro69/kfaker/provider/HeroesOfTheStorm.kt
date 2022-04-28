package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.HEROES_OF_THE_STORM] category.
 */
@Suppress("unused")
class HeroesOfTheStorm internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<HeroesOfTheStorm>(fakerService) {
    override val category = YamlCategory.HEROES_OF_THE_STORM
    override val localUniqueDataProvider = LocalUniqueDataProvider<HeroesOfTheStorm>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun battlegrounds() = resolve("battlegrounds")
    fun classNames() = resolve("class_names")
    fun heroes() = resolve("heroes")
    fun quotes() = resolve("quotes")
}
