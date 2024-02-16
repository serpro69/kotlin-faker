@file:Suppress("unused")

package io.github.serpro69.kfaker.movies.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.RandomService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.TRON] category.
 */
class Tron internal constructor(
    fakerService: FakerService,
    private val randomService: RandomService
) : YamlFakeDataProvider<Tron>(fakerService) {
    override val yamlCategory = YamlCategory.TRON
    override val localUniqueDataProvider = LocalUniqueDataProvider<Tron>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun characters(type: TronCharacterType = randomService.nextEnum()): String =
        resolve("characters", type.name.lowercase())

    fun games(): String = resolve("games")
    fun locations(): String = resolve("locations")

    fun quotes(character: TronCharacter = randomService.nextEnum()): String =
        resolve("quotes", character.name.lowercase())

    fun taglines(): String = resolve("taglines")
    fun vehicles(): String = resolve("vehicles")

    fun alternateCharacterSpellings(character: TronAlternateCharacter = randomService.nextEnum()): String =
        resolve("alternate_character_spellings", character.name.lowercase())

    // todo functions with enum type parameters should by default return a random value?
}

enum class TronAlternateCharacter {
    ALAN_BRADLEY,
    CLU,
    DR_LORA_BAINES,
    DR_WALTER_GIBBS,
    ED_DILLINGER,
    KEVIN_FLYNN,
    MCP,
    ROY_KLEINBERG,
    ;
}

enum class TronCharacter {
    ALAN_BRADLEY,
    BIT,
    CLU,
    CROM,
    DR_LORA_BAINES,
    DR_WALTER_GIBBS,
    DUMONT,
    ED_DILLINGER,
    KEVIN_FLYNN,
    MCP,
    PROGRAM,
    RAM,
    SARK,
    TRON,
    YORI,
    ;
}

enum class TronCharacterType {
    OTHER,
    PROGRAMS,
    USERS,
    ;
}
