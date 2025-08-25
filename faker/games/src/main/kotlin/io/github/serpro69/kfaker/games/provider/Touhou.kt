@file:Suppress("unused")

package io.github.serpro69.kfaker.games.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.Category
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/** [FakeDataProvider] implementation for [YamlCategory.GAMES] category. */
class Touhou internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<Touhou>(fakerService) {
    override val yamlCategory = YamlCategory.GAMES
    override val secondaryCategory: Category = Category.ofName("TOUHOU")
    override val localUniqueDataProvider = LocalUniqueDataProvider<Touhou>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory, secondaryCategory)
    }

    fun games(): String = resolve(secondaryCategory, "games")

    fun characters(): String = resolve(secondaryCategory, "characters")

    fun spellCards(): String = resolve(secondaryCategory, "spell_cards")

    fun locations(): String = resolve(secondaryCategory, "locations")

    fun songs(): String = resolve(secondaryCategory, "songs")
}
