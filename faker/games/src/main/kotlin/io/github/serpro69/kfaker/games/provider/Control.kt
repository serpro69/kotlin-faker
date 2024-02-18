package io.github.serpro69.kfaker.games.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.Category
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.GAMES] category.
 */
@Suppress("unused")
class Control internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Control>(fakerService) {
    override val yamlCategory = YamlCategory.GAMES
    override val secondaryCategory: Category = Category.ofName("CONTROL")
    override val localUniqueDataProvider = LocalUniqueDataProvider<Control>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory, secondaryCategory)
    }

    fun character() = resolve(secondaryCategory, "character")
    fun location() = resolve(secondaryCategory, "location")
    fun objectOfPower() = resolve(secondaryCategory, "object_of_power")
    fun alteredItem() = resolve(secondaryCategory, "altered_item")
    fun alteredWorldEvent() = resolve(secondaryCategory, "altered_world_event")
    fun hiss() = resolve(secondaryCategory, "hiss")
    fun theBoard() = resolve(secondaryCategory, "the_board")
    fun quote() = resolve(secondaryCategory, "quote")
}
