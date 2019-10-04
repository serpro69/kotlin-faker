package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.GAMES] category.
 */
@Suppress("unused")
class LeagueOfLegends internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<LeagueOfLegends>(fakerService) {
    override val categoryName = CategoryName.GAMES
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val champion = resolve { fakerService.resolve(it, "league_of_legends", "champion") }
    val location = resolve { fakerService.resolve(it, "league_of_legends", "location") }
    val quote = resolve { fakerService.resolve(it, "league_of_legends", "quote") }
    val summonerSpell = resolve { fakerService.resolve(it, "league_of_legends", "summoner_spell") }
    val masteries = resolve { fakerService.resolve(it, "league_of_legends", "masteries") }
    val rank = resolve { fakerService.resolve(it, "league_of_legends", "rank") }
}
