package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.STAR_WARS] category.
 */
@Suppress("unused")
class StarWars internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.STAR_WARS

    val characters = resolve { fakerService.resolve(it, "characters") }
    val callSquadrons = resolve { fakerService.resolve(it, "call_squadrons") }
    val callNumbers = resolve { fakerService.resolve(it, "call_numbers") }
    val callSign = resolve { fakerService.resolve(it, "call_sign") }
    val droids = resolve { fakerService.resolve(it, "droids") }
    val planets = resolve { fakerService.resolve(it, "planets") }
    val species = resolve { fakerService.resolve(it, "species") }
    val vehicles = resolve { fakerService.resolve(it, "vehicles") }
    val wookieeWords = resolve { fakerService.resolve(it, "wookiee_words") }
    val quotes: (character: String) -> String = { character ->
        resolve { fakerService.resolve(it, "quotes", character.toLowerCase().replace("_", " ")) }.invoke()
    }
    val quote = resolve { fakerService.resolve(it, "quotes", "") }
    val alternateCharacterSpellings: (character: String) -> String = { character ->
        resolve { fakerService.resolve(it, "alternate_character_spellings", character) }.invoke()
    }
}
