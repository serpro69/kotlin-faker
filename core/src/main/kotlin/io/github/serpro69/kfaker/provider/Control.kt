package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.GAMES] category.
 */
@Suppress("unused")
class Control internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Control>(fakerService) {
    override val category = YamlCategory.GAMES
    override val localUniqueDataProvider = LocalUniqueDataProvider<Control>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun character() = resolve("control", "character")
    fun location() = resolve("control", "location")
    fun objectOfPower() = resolve("control", "object_of_power")
    fun alteredItem() = resolve("control", "altered_item")
    fun alteredWorldEvent() = resolve("control", "altered_world_event")
    fun hiss() = resolve("control", "hiss")
    fun theBoard() = resolve("control", "the_board")
    fun quote() = resolve("control", "quote")
}
