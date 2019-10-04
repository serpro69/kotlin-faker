package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.STAR_WARS] category.
 */
@Suppress("unused")
class StarWars internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<StarWars>(fakerService) {
    override val categoryName = CategoryName.STAR_WARS
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val characters = resolve("characters")
    val callSquadrons = resolve("call_squadrons")
    val callNumbers = resolve("call_numbers")
    val callSign = resolve("call_sign")
    val droids = resolve("droids")
    val planets = resolve("planets")
    val species = resolve("species")
    val vehicles = resolve("vehicles")
    val wookieeWords = resolve("wookiee_words")
    val quotes: (character: String) -> String = { character ->
        resolve("quotes", character.toLowerCase().replace("_", " "))
    }
    val quote = resolve("quotes", "")
    val alternateCharacterSpellings: (character: String) -> String = { character ->
        resolve("alternate_character_spellings", character)
    }
}
