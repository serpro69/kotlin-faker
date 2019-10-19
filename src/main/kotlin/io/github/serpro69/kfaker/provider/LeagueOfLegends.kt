package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.GAMES] category.
 */
@Suppress("unused")
class LeagueOfLegends internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<LeagueOfLegends>(fakerService) {
    override val categoryName = CategoryName.GAMES
    override val localUniqueDataProvider = LocalUniqueDataProvider<LeagueOfLegends>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun champion() = resolve("league_of_legends", "champion")
    fun location() = resolve("league_of_legends", "location")
    fun quote() = resolve("league_of_legends", "quote")
    fun summonerSpell() = resolve("league_of_legends", "summoner_spell")
    fun masteries() = resolve("league_of_legends", "masteries")
    fun rank() = resolve("league_of_legends", "rank")
}
