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
class Overwatch internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Overwatch>(fakerService) {
    override val yamlCategory = YamlCategory.GAMES
    override val secondaryCategory: Category = Category.ofName("OVERWATCH")
    override val localUniqueDataProvider = LocalUniqueDataProvider<Overwatch>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory, secondaryCategory)
    }

    fun heroes() = resolve(secondaryCategory, "heroes")
    fun locations() = resolve(secondaryCategory, "locations")
    fun quotes() = resolve(secondaryCategory, "quotes")
}
