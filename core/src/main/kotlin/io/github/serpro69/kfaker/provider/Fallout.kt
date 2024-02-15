package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.GAMES] category.
 */
@Suppress("unused")
class Fallout internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Fallout>(fakerService) {
    override val yamlCategory = YamlCategory.GAMES
    override val secondaryCategory: Category = Category.ofName("FALLOUT")
    override val localUniqueDataProvider = LocalUniqueDataProvider<Fallout>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory, secondaryCategory)
    }

    fun characters() = resolve(secondaryCategory, "characters")
    fun factions() = resolve(secondaryCategory, "factions")
    fun locations() = resolve(secondaryCategory, "locations")
    fun quotes() = resolve(secondaryCategory, "quotes")
}
