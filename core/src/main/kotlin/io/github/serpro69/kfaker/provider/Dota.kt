package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.GAMES] category.
 */
@Suppress("unused")
class Dota internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Dota>(fakerService) {
    override val yamlCategory = YamlCategory.GAMES
    override val secondaryCategory: Category = Category.ofName("DOTA")
    override val localUniqueDataProvider = LocalUniqueDataProvider<Dota>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory, secondaryCategory)
    }

    fun building() = resolve(secondaryCategory, "building")

    fun hero() = resolve(secondaryCategory, "hero")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun abaddon() = resolve(secondaryCategory, "abaddon", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun alchemist() = resolve(secondaryCategory, "alchemist", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun axe() = resolve(secondaryCategory, "axe", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun beastmaster() = resolve(secondaryCategory, "beastmaster", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun brewmaster() = resolve(secondaryCategory, "brewmaster", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun bristleback() = resolve(secondaryCategory, "bristleback", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun centaur() = resolve(secondaryCategory, "centaur", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun chaosKnight() = resolve(secondaryCategory, "chaos_knight", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun clockwerk() = resolve(secondaryCategory, "clockwerk", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun doom() = resolve(secondaryCategory, "doom", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun dragonKnight() = resolve(secondaryCategory, "dragon_knight", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun earthSpirit() = resolve(secondaryCategory, "earth_spirit", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun earthshaker() = resolve(secondaryCategory, "earthshaker", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun elderTitan() = resolve(secondaryCategory, "elder_titan", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun huskar() = resolve(secondaryCategory, "huskar", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun io() = resolve(secondaryCategory, "io", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun kunkka() = resolve(secondaryCategory, "kunkka", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun legionCommander() = resolve(secondaryCategory, "legion_commander", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun lifestealer() = resolve(secondaryCategory, "lifestealer", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun lycan() = resolve(secondaryCategory, "lycan", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun magnus() = resolve(secondaryCategory, "magnus", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun meepo() = resolve(secondaryCategory, "meepo", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun nightStalker() = resolve(secondaryCategory, "night_stalker", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun omniknight() = resolve(secondaryCategory, "omniknight", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun phoenix() = resolve(secondaryCategory, "phoenix", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun pudge() = resolve(secondaryCategory, "pudge", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun sandKing() = resolve(secondaryCategory, "sand_king", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun slardar() = resolve(secondaryCategory, "slardar", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun spiritBreaker() = resolve(secondaryCategory, "spirit_breaker", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun sven() = resolve(secondaryCategory, "sven", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun tidehunter() = resolve(secondaryCategory, "tidehunter", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun timbersaw() = resolve(secondaryCategory, "timbersaw", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun tiny() = resolve(secondaryCategory, "tiny", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun treantProtector() = resolve(secondaryCategory, "treant_protector", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun tusk() = resolve(secondaryCategory, "tusk", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun underlord() = resolve(secondaryCategory, "underlord", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun undying() = resolve(secondaryCategory, "undying", "quote")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun wraithKing() = resolve(secondaryCategory, "wraith_king", "quote")

    fun item() = resolve(secondaryCategory, "item")

    fun team() = resolve(secondaryCategory, "team")

    fun player() = resolve(secondaryCategory, "player")
}
