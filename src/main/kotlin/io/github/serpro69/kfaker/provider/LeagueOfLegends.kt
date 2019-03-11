package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.GAMES] category.
 */
class LeagueOfLegends internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.GAMES

    val champion = resolve { fakerService.resolve(Faker, it, "league_of_legends", "champion") }
    val location = resolve { fakerService.resolve(Faker, it, "league_of_legends", "location") }
    val quote = resolve { fakerService.resolve(Faker, it, "league_of_legends", "quote") }
    val summonerSpell = resolve { fakerService.resolve(Faker, it, "league_of_legends", "summoner_spell") }
    val masteries = resolve { fakerService.resolve(Faker, it, "league_of_legends", "masteries") }
    val rank = resolve { fakerService.resolve(Faker, it, "league_of_legends", "rank") }
}
