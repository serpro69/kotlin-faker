@file:Suppress("unused")

package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.TRON] category.
 */
class Tron internal constructor(fakerService: FakerService) :
    AbstractFakeDataProvider<Tron>(fakerService) {
    override val category = YamlCategory.TRON
    override val localUniqueDataProvider = LocalUniqueDataProvider<Tron>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun characters(type: TronCharacterType = fakerService.randomService.nextEnum()): String =
        resolve("characters", type.name.lowercase())

    fun games(): String = resolve("games")
    fun locations(): String = resolve("locations")

    fun quotes(character: TronCharacter = fakerService.randomService.nextEnum()): String =
        resolve("quotes", character.name.lowercase())

    fun taglines(): String = resolve("taglines")
    fun vehicles(): String = resolve("vehicles")

    fun alternateCharacterSpellings(character: TronAlternateCharacter = fakerService.randomService.nextEnum()): String =
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
