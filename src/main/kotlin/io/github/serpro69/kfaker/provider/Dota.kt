package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.GAMES] category.
 */
class Dota internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.GAMES

    val hero = resolve { fakerService.resolve(Faker, it, "dota", "hero") }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val abaddon = resolve { fakerService.resolve(Faker, it, "dota", "abaddon", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val alchemist = resolve { fakerService.resolve(Faker, it, "dota", "alchemist", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val axe = resolve { fakerService.resolve(Faker, it, "dota", "axe", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val beastmaster = resolve { fakerService.resolve(Faker, it, "dota", "beastmaster", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val brewmaster = resolve { fakerService.resolve(Faker, it, "dota", "brewmaster", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val bristleback = resolve { fakerService.resolve(Faker, it, "dota", "bristleback", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val centaur = resolve { fakerService.resolve(Faker, it, "dota", "centaur", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val chaosKnight = resolve { fakerService.resolve(Faker, it, "dota", "chaos_knight", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val clockwerk = resolve { fakerService.resolve(Faker, it, "dota", "clockwerk", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val doom = resolve { fakerService.resolve(Faker, it, "dota", "doom", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val dragonKnight = resolve { fakerService.resolve(Faker, it, "dota", "dragon_knight", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val earthSpirit = resolve { fakerService.resolve(Faker, it, "dota", "earth_spirit", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val earthshaker = resolve { fakerService.resolve(Faker, it, "dota", "earthshaker", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val elderTitan = resolve { fakerService.resolve(Faker, it, "dota", "elder_titan", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val huskar = resolve { fakerService.resolve(Faker, it, "dota", "huskar", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val io = resolve { fakerService.resolve(Faker, it, "dota", "io", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val kunkka = resolve { fakerService.resolve(Faker, it, "dota", "kunkka", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val legionCommander = resolve { fakerService.resolve(Faker, it, "dota", "legion_commander", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val lifestealer = resolve { fakerService.resolve(Faker, it, "dota", "lifestealer", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val lycan = resolve { fakerService.resolve(Faker, it, "dota", "lycan", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val magnus = resolve { fakerService.resolve(Faker, it, "dota", "magnus", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val nightStalker = resolve { fakerService.resolve(Faker, it, "dota", "night_stalker", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val omniknight = resolve { fakerService.resolve(Faker, it, "dota", "omniknight", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val phoenix = resolve { fakerService.resolve(Faker, it, "dota", "phoenix", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val pudge = resolve { fakerService.resolve(Faker, it, "dota", "pudge", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val sandKing = resolve { fakerService.resolve(Faker, it, "dota", "sand_king", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val slardar = resolve { fakerService.resolve(Faker, it, "dota", "slardar", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val spiritBreaker = resolve { fakerService.resolve(Faker, it, "dota", "spirit_breaker", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val sven = resolve { fakerService.resolve(Faker, it, "dota", "sven", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val tidehunter = resolve { fakerService.resolve(Faker, it, "dota", "tidehunter", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val timbersaw = resolve { fakerService.resolve(Faker, it, "dota", "timbersaw", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val tiny = resolve { fakerService.resolve(Faker, it, "dota", "tiny", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val treantProtector = resolve { fakerService.resolve(Faker, it, "dota", "treant_protector", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val tusk = resolve { fakerService.resolve(Faker, it, "dota", "tusk", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val underlord = resolve { fakerService.resolve(Faker, it, "dota", "underlord", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val undying = resolve { fakerService.resolve(Faker, it, "dota", "undying", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val wraithKing = resolve { fakerService.resolve(Faker, it, "dota", "wraith_king", "quote") }

    val item = resolve { fakerService.resolve(Faker, it, "dota", "item") }

    val team = resolve { fakerService.resolve(Faker, it, "dota", "team") }

    val player = resolve { fakerService.resolve(Faker, it, "dota", "player") }
}
