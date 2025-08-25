package io.github.serpro69.kfaker.movies.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/** [FakeDataProvider] implementation for [YamlCategory.STAR_WARS] category. */
@Suppress("unused")
class StarWars internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<StarWars>(fakerService) {
    override val yamlCategory = YamlCategory.STAR_WARS
    override val localUniqueDataProvider = LocalUniqueDataProvider<StarWars>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun characters() = resolve("characters")

    fun callSquadrons() = resolve("call_squadrons")

    fun callNumbers() = with(fakerService) { resolve("call_numbers").numerify() }

    fun callSign() = with(fakerService) { resolve("call_sign").numerify() }

    fun droids() = resolve("droids")

    fun planets() = resolve("planets")

    fun species() = resolve("species")

    fun vehicles() = resolve("vehicles")

    fun wookieeWords() = resolve("wookiee_words")

    fun quotes(character: String) = resolve("quotes", character.lowercase().replace(" ", "_"))

    fun quote() = resolve("quotes", "")

    fun alternateCharacterSpellings(character: String) =
        resolve("alternate_character_spellings", character)
}
