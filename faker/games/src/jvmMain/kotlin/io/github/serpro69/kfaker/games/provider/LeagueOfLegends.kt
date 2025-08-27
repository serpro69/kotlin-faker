package io.github.serpro69.kfaker.games.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.Category
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/** [FakeDataProvider] implementation for [YamlCategory.GAMES] category. */
@Suppress("unused")
class LeagueOfLegends internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<LeagueOfLegends>(fakerService) {
    override val yamlCategory = YamlCategory.GAMES
    override val secondaryCategory: Category = Category.ofName("LEAGUE_OF_LEGENDS")
    override val localUniqueDataProvider = LocalUniqueDataProvider<LeagueOfLegends>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory, secondaryCategory)
    }

    fun champion() = resolve(secondaryCategory, "champion")

    fun location() = resolve(secondaryCategory, "location")

    fun quote() = resolve(secondaryCategory, "quote")

    fun summonerSpell() = resolve(secondaryCategory, "summoner_spell")

    fun masteries() = resolve(secondaryCategory, "masteries")

    fun rank() = resolve(secondaryCategory, "rank")
}
