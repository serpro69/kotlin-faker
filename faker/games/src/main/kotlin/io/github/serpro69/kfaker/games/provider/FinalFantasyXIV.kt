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
class FinalFantasyXIV internal constructor(fakerService: FakerService) : YamlFakeDataProvider<FinalFantasyXIV>(fakerService) {
    override val yamlCategory = YamlCategory.GAMES
    override val secondaryCategory: Category = Category.ofName("FINAL_FANTASY_XIV")
    override val localUniqueDataProvider = LocalUniqueDataProvider<FinalFantasyXIV>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory, secondaryCategory)
    }

    fun characters(): String = resolve(secondaryCategory, "characters")
    fun jobs(): String = resolve(secondaryCategory, "jobs")
    fun races(): String = resolve(secondaryCategory, "races")
    fun dataCenters(): String = resolve(secondaryCategory, "data_centers")
    fun zones(): String = resolve(secondaryCategory, "zones")
}
