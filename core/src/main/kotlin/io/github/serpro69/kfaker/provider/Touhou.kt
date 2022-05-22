@file:Suppress("unused")

package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.GAMES] category.
 */
class Touhou internal constructor(fakerService: FakerService) :
    AbstractFakeDataProvider<Touhou>(fakerService) {
    override val category = YamlCategory.GAMES
    override val localUniqueDataProvider = LocalUniqueDataProvider<Touhou>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun games(): String = resolve("touhou", "games")
    fun characters(): String = resolve("touhou", "characters")
    fun spellCards(): String = resolve("touhou", "spell_cards")
    fun locations(): String = resolve("touhou", "locations")
    fun songs(): String = resolve("touhou", "songs")
}