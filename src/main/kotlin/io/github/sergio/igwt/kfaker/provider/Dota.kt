package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.GAMES] category.
 */
@Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
class Dota internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.GAMES

    // TODO: 3/7/2019 Need to implement resolving by 3 keys
    val hero = resolve { fakerService.resolve(Faker, it, "dota", "hero", "quote") }
    val abaddon = resolve { fakerService.resolve(Faker, it, "dota", "abaddon", "quote") }
    val alchemist = resolve { fakerService.resolve(Faker, it, "dota", "alchemist", "quote") }
    val axe = resolve { fakerService.resolve(Faker, it, "dota", "axe", "quote") }
    val beastmaster = resolve { fakerService.resolve(Faker, it, "dota", "beastmaster", "quote") }
    val brewmaster = resolve { fakerService.resolve(Faker, it, "dota", "brewmaster", "quote") }
    val bristleback = resolve { fakerService.resolve(Faker, it, "dota", "bristleback", "quote") }
    val centaur = resolve { fakerService.resolve(Faker, it, "dota", "centaur", "quote") }
    val chaosKnight = resolve { fakerService.resolve(Faker, it, "dota", "chaos_knight", "quote") }
    val clockwerk = resolve { fakerService.resolve(Faker, it, "dota", "clockwerk", "quote") }
    val doom = resolve { fakerService.resolve(Faker, it, "dota", "doom", "quote") }
    val dragonKnight = resolve { fakerService.resolve(Faker, it, "dota", "dragon_knight", "quote") }
    val earthSpirit = resolve { fakerService.resolve(Faker, it, "dota", "earth_spirit", "quote") }
    val earthshaker = resolve { fakerService.resolve(Faker, it, "dota", "earthshaker", "quote") }
    val elderTitan = resolve { fakerService.resolve(Faker, it, "dota", "elder_titan", "quote") }
    val huskar = resolve { fakerService.resolve(Faker, it, "dota", "huskar", "quote") }
    val io = resolve { fakerService.resolve(Faker, it, "dota", "io", "quote") }
    val kunkka = resolve { fakerService.resolve(Faker, it, "dota", "kunkka", "quote") }
    val legionCommander = resolve { fakerService.resolve(Faker, it, "dota", "legion_commander", "quote") }
    val lifestealer = resolve { fakerService.resolve(Faker, it, "dota", "lifestealer", "quote") }
    val lycan = resolve { fakerService.resolve(Faker, it, "dota", "lycan", "quote") }
    val magnus = resolve { fakerService.resolve(Faker, it, "dota", "magnus", "quote") }
    val nightStalker = resolve { fakerService.resolve(Faker, it, "dota", "night_stalker", "quote") }
    val omniknight = resolve { fakerService.resolve(Faker, it, "dota", "omniknight", "quote") }
    val phoenix = resolve { fakerService.resolve(Faker, it, "dota", "phoenix", "quote") }
    val pudge = resolve { fakerService.resolve(Faker, it, "dota", "pudge", "quote") }
    val sandKing = resolve { fakerService.resolve(Faker, it, "dota", "sand_king", "quote") }
    val slardar = resolve { fakerService.resolve(Faker, it, "dota", "slardar", "quote") }
    val spiritBreaker = resolve { fakerService.resolve(Faker, it, "dota", "spirit_breaker", "quote") }
    val sven = resolve { fakerService.resolve(Faker, it, "dota", "sven", "quote") }
    val tidehunter = resolve { fakerService.resolve(Faker, it, "dota", "tidehunter", "quote") }
    val timbersaw = resolve { fakerService.resolve(Faker, it, "dota", "timbersaw", "quote") }
    val tiny = resolve { fakerService.resolve(Faker, it, "dota", "tiny", "quote") }
    val treantProtector = resolve { fakerService.resolve(Faker, it, "dota", "treant_protector", "quote") }
    val tusk = resolve { fakerService.resolve(Faker, it, "dota", "tusk", "quote") }
    val underlord = resolve { fakerService.resolve(Faker, it, "dota", "underlord", "quote") }
    val undying = resolve { fakerService.resolve(Faker, it, "dota", "undying", "quote") }
    val wraithKing = resolve { fakerService.resolve(Faker, it, "dota", "wraith_king", "quote") }
    val item = resolve { fakerService.resolve(Faker, it, "dota", "item") }
    val team = resolve { fakerService.resolve(Faker, it, "dota", "team") }
    val player = resolve { fakerService.resolve(Faker, it, "dota", "player") }
}
