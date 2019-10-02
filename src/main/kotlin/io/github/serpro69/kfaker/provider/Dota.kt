package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.GAMES] category.
 */
@Suppress("unused")
class Dota internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.GAMES

    val hero = resolve { fakerService.resolve(it, "dota", "hero") }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val abaddon = resolve { fakerService.resolve(it, "dota", "abaddon", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val alchemist = resolve { fakerService.resolve(it, "dota", "alchemist", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val axe = resolve { fakerService.resolve(it, "dota", "axe", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val beastmaster = resolve { fakerService.resolve(it, "dota", "beastmaster", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val brewmaster = resolve { fakerService.resolve(it, "dota", "brewmaster", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val bristleback = resolve { fakerService.resolve(it, "dota", "bristleback", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val centaur = resolve { fakerService.resolve(it, "dota", "centaur", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val chaosKnight = resolve { fakerService.resolve(it, "dota", "chaos_knight", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val clockwerk = resolve { fakerService.resolve(it, "dota", "clockwerk", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val doom = resolve { fakerService.resolve(it, "dota", "doom", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val dragonKnight = resolve { fakerService.resolve(it, "dota", "dragon_knight", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val earthSpirit = resolve { fakerService.resolve(it, "dota", "earth_spirit", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val earthshaker = resolve { fakerService.resolve(it, "dota", "earthshaker", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val elderTitan = resolve { fakerService.resolve(it, "dota", "elder_titan", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val huskar = resolve { fakerService.resolve(it, "dota", "huskar", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val io = resolve { fakerService.resolve(it, "dota", "io", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val kunkka = resolve { fakerService.resolve(it, "dota", "kunkka", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val legionCommander = resolve { fakerService.resolve(it, "dota", "legion_commander", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val lifestealer = resolve { fakerService.resolve(it, "dota", "lifestealer", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val lycan = resolve { fakerService.resolve(it, "dota", "lycan", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val magnus = resolve { fakerService.resolve(it, "dota", "magnus", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val nightStalker = resolve { fakerService.resolve(it, "dota", "night_stalker", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val omniknight = resolve { fakerService.resolve(it, "dota", "omniknight", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val phoenix = resolve { fakerService.resolve(it, "dota", "phoenix", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val pudge = resolve { fakerService.resolve(it, "dota", "pudge", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val sandKing = resolve { fakerService.resolve(it, "dota", "sand_king", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val slardar = resolve { fakerService.resolve(it, "dota", "slardar", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val spiritBreaker = resolve { fakerService.resolve(it, "dota", "spirit_breaker", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val sven = resolve { fakerService.resolve(it, "dota", "sven", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val tidehunter = resolve { fakerService.resolve(it, "dota", "tidehunter", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val timbersaw = resolve { fakerService.resolve(it, "dota", "timbersaw", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val tiny = resolve { fakerService.resolve(it, "dota", "tiny", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val treantProtector = resolve { fakerService.resolve(it, "dota", "treant_protector", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val tusk = resolve { fakerService.resolve(it, "dota", "tusk", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val underlord = resolve { fakerService.resolve(it, "dota", "underlord", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val undying = resolve { fakerService.resolve(it, "dota", "undying", "quote") }
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val wraithKing = resolve { fakerService.resolve(it, "dota", "wraith_king", "quote") }

    val item = resolve { fakerService.resolve(it, "dota", "item") }

    val team = resolve { fakerService.resolve(it, "dota", "team") }

    val player = resolve { fakerService.resolve(it, "dota", "player") }
}
