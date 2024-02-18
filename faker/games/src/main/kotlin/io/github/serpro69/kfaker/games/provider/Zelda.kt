package io.github.serpro69.kfaker.games.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.Category
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.GAMES] category.
 */
@Suppress("unused")
class Zelda internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Zelda>(fakerService) {
    override val yamlCategory = YamlCategory.GAMES
    override val secondaryCategory: Category = Category.ofName("ZELDA")
    override val localUniqueDataProvider = LocalUniqueDataProvider<Zelda>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory, secondaryCategory)
    }

    fun games() = resolve(secondaryCategory, "games")
    fun characters() = resolve(secondaryCategory, "characters")
    fun locations() = resolve(secondaryCategory, "locations")
    fun items() = resolve(secondaryCategory, "items")
}
