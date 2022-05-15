package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.DUNE] category.
 */
@Suppress("unused")
class Dune internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Dune>(fakerService) {
    override val category = YamlCategory.DUNE
    override val localUniqueDataProvider = LocalUniqueDataProvider<Dune>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

//    fun characters() = resolve("characters")
//    fun titles() = resolve("titles")
//    fun planets() = resolve("planets")
//    fun cities() = resolve("cities")

    fun quotes(character: DuneQuoteCharacter = fakerService.randomService.nextEnum()) =
        resolve("quotes", character.name.lowercase())

//    @Deprecated(
//        message = "Deprecated and will be removed in future releases.",
//        ReplaceWith("quotes(DuneQuoteCharacter.PAUL)", "io.github.serpro69.kfaker.provider.DuneQuoteCharacter"),
//        level = DeprecationLevel.WARNING,
//    )
//    fun quotes(character: String) = DuneQuoteCharacter.values().firstOrNull { it.name.equals(character, true) }?.let {
//        quotes(it)
//    } ?: throw IllegalArgumentException("Dune quote not found for '$character'")
//
//    fun sayings(origin: DuneSayingOrigin = fakerService.randomService.nextEnum()) =
//        resolve("sayings", origin.name.lowercase())
//
//    @Deprecated(
//        message = "Deprecated and will be removed in future releases.",
//        ReplaceWith("sayings(DuneSayingOrigin.FREMEN)", "io.github.serpro69.kfaker.provider.DuneSayingOrigin"),
//        level = DeprecationLevel.WARNING,
//    )
//    fun sayings(origin: String) = DuneSayingOrigin.values().firstOrNull { it.name.equals(origin, true) }?.let {
//        sayings(it)
//    } ?: throw IllegalArgumentException("Dune saying not found for '$origin'")
}

enum class DuneQuoteCharacter {
    GUILD_NAVIGATOR,
    EMPEROR,
    PAUL,
    THUFIR,
    JESSICA,
    IRULAN,
    MOHIAM,
    GURNEY,
    LETO,
    STILGAR,
    LIET_KYNES,
    PARDOT_KYNES,
    BARON_HARKONNEN,
    PITER,
    ALIA,
    MAPES,
    DUNCAN,
    YUEH,
    ;
}

enum class DuneSayingOrigin {
    BENE_GESSERIT,
    FREMEN,
    MENTAT,
    MUADDIB,
    ORANGE_CATHOLIC_BIBLE,
    ;
}
