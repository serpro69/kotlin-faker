package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.STAR_WARS] category.
 */
@Suppress("unused")
class StarWars internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<StarWars>(fakerService) {
    override val categoryName = CategoryName.STAR_WARS
    override val localUniqueDataProvider = LocalUniqueDataProvider<StarWars>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun characters() = resolve("characters")
    fun callSquadrons() = resolve("call_squadrons")
    fun callNumbers() = resolve("call_numbers")
    fun callSign() = resolve("call_sign")
    fun droids() = resolve("droids")
    fun planets() = resolve("planets")
    fun species() = resolve("species")
    fun vehicles() = resolve("vehicles")
    fun wookieeWords() = resolve("wookiee_words")
    fun quotes(character: String) = resolve("quotes", character.toLowerCase().replace("_", " "))
    fun quote() = resolve("quotes", "")
    fun alternateCharacterSpellings(character: String) = resolve("alternate_character_spellings", character)
}
