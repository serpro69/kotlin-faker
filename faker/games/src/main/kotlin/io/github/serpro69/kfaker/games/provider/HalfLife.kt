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
class HalfLife internal constructor(fakerService: FakerService) : YamlFakeDataProvider<HalfLife>(fakerService) {
    override val yamlCategory = YamlCategory.GAMES
    override val secondaryCategory: Category = Category.ofName("HALF_LIFE")
    override val localUniqueDataProvider = LocalUniqueDataProvider<HalfLife>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory, secondaryCategory)
    }

    fun character() = resolve(secondaryCategory, "character")
    fun enemy() = resolve(secondaryCategory, "enemy")
    fun location() = resolve(secondaryCategory, "location")
}
