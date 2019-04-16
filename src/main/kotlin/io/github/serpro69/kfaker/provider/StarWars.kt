package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.STAR_WARS] category.
 */
class StarWars internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.STAR_WARS

    val characters = resolve { fakerService.resolve(Faker, it, "characters") }
    val callSquadrons = resolve { fakerService.resolve(Faker, it, "call_squadrons") }
    val callNumbers = resolve { fakerService.resolve(Faker, it, "call_numbers") }
    val callSign = resolve { fakerService.resolve(Faker, it, "call_sign") }
    val droids = resolve { fakerService.resolve(Faker, it, "droids") }
    val planets = resolve { fakerService.resolve(Faker, it, "planets") }
    val species = resolve { fakerService.resolve(Faker, it, "species") }
    val vehicles = resolve { fakerService.resolve(Faker, it, "vehicles") }
    val wookieeWords = resolve { fakerService.resolve(Faker, it, "wookiee_words") }
    val quotes: (character: String) -> String = { character ->
        resolve { fakerService.resolve(Faker, it, "quotes", character.toLowerCase().replace("_", " ")) }.invoke()
    }
    val quote = resolve { fakerService.resolve(Faker, it , "quotes", "") }
    val alternateCharacterSpellings: (character: String) -> String = { character ->
        resolve { fakerService.resolve(Faker, it, "alternate_character_spellings", character) }.invoke()
    }
}
