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
class ClashOfClans internal constructor(fakerService: FakerService) : YamlFakeDataProvider<ClashOfClans>(fakerService) {
    override val yamlCategory = YamlCategory.GAMES
    override val secondaryCategory: Category = Category.ofName("CLASH_OF_CLAN") // name due to yaml filename typo
    override val localUniqueDataProvider = LocalUniqueDataProvider<ClashOfClans>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory, secondaryCategory)
    }

    fun troops(): String = resolve("clash_of_clans", "troops")
    fun ranks(): String = resolve("clash_of_clans", "ranks")
    fun defensiveBuildings(): String = resolve("clash_of_clans", "defensive_buildings")
}

