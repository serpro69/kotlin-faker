package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
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
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

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
