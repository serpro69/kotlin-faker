@file:Suppress("unused")

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
class SuperMario internal constructor(fakerService: FakerService) : YamlFakeDataProvider<SuperMario>(fakerService) {
    override val yamlCategory = YamlCategory.GAMES
    override val secondaryCategory: Category = Category.ofName("SUPER_MARIO")
    override val localUniqueDataProvider = LocalUniqueDataProvider<SuperMario>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory, secondaryCategory)
    }

    fun characters(): String = resolve(secondaryCategory, "characters")
    fun games(): String = resolve(secondaryCategory, "games")
    fun locations(): String = resolve(secondaryCategory, "locations")
}
