@file:Suppress("unused")

package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.Category
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.GAMES] category.
 */
class FinalFantasyXIV internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<FinalFantasyXIV>(fakerService) {
    override val yamlCategory = YamlCategory.GAMES
    override val secondaryCategory: Category = Category.ofName("FINAL_FANTASY_XIV")
    override val localUniqueDataProvider = LocalUniqueDataProvider<FinalFantasyXIV>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory, secondaryCategory)
    }

    fun characters(): String = resolve("final_fantasy_xiv", "characters")
    fun jobs(): String = resolve("final_fantasy_xiv", "jobs")
    fun races(): String = resolve("final_fantasy_xiv", "races")
    fun dataCenters(): String = resolve("final_fantasy_xiv", "data_centers")
    fun zones(): String = resolve("final_fantasy_xiv", "zones")
}
