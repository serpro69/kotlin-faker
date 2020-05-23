package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.GAMES] category.
 */
@Suppress("unused")
class Dota internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Dota>(fakerService) {
    override val categoryName = CategoryName.GAMES
    override val localUniqueDataProvider = LocalUniqueDataProvider<Dota>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun hero() = resolve("dota", "hero")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun abaddon() = resolve("dota", "abaddon", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun alchemist() = resolve("dota", "alchemist", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun axe() = resolve("dota", "axe", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun beastmaster() = resolve("dota", "beastmaster", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun brewmaster() = resolve("dota", "brewmaster", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun bristleback() = resolve("dota", "bristleback", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun centaur() = resolve("dota", "centaur", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun chaosKnight() = resolve("dota", "chaos_knight", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun clockwerk() = resolve("dota", "clockwerk", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun doom() = resolve("dota", "doom", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun dragonKnight() = resolve("dota", "dragon_knight", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun earthSpirit() = resolve("dota", "earth_spirit", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun earthshaker() = resolve("dota", "earthshaker", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun elderTitan() = resolve("dota", "elder_titan", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun huskar() = resolve("dota", "huskar", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun io() = resolve("dota", "io", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun kunkka() = resolve("dota", "kunkka", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun legionCommander() = resolve("dota", "legion_commander", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun lifestealer() = resolve("dota", "lifestealer", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun lycan() = resolve("dota", "lycan", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun magnus() = resolve("dota", "magnus", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun meepo() = resolve("dota", "meepo", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun nightStalker() = resolve("dota", "night_stalker", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun omniknight() = resolve("dota", "omniknight", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun phoenix() = resolve("dota", "phoenix", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun pudge() = resolve("dota", "pudge", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun sandKing() = resolve("dota", "sand_king", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun slardar() = resolve("dota", "slardar", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun spiritBreaker() = resolve("dota", "spirit_breaker", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun sven() = resolve("dota", "sven", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun tidehunter() = resolve("dota", "tidehunter", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun timbersaw() = resolve("dota", "timbersaw", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun tiny() = resolve("dota", "tiny", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun treantProtector() = resolve("dota", "treant_protector", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun tusk() = resolve("dota", "tusk", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun underlord() = resolve("dota", "underlord", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun undying() = resolve("dota", "undying", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun wraithKing() = resolve("dota", "wraith_king", "quote")

    fun item() = resolve("dota", "item")

    fun team() = resolve("dota", "team")

    fun player() = resolve("dota", "player")
}
