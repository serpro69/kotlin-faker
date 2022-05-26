package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.GAMES] category.
 */
@Suppress("unused")
class Minecraft internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Minecraft>(fakerService) {
    override val yamlCategory = YamlCategory.GAMES
    override val secondaryCategory: Category = Category.ofName("MINECRAFT")
    override val localUniqueDataProvider = LocalUniqueDataProvider<Minecraft>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory, secondaryCategory)
    }

    fun achievement() = resolve("minecraft", "achievement")
    fun biome() = resolve("minecraft", "biome")
    fun blocks() = resolve("minecraft", "blocks")
    fun enchantment() = resolve("minecraft", "enchantment")
    fun gameMode() = resolve("minecraft", "game_mode")
    fun items() = resolve("minecraft", "items")
    fun mobs() = resolve("minecraft", "mobs")
    fun statusEffect() = resolve("minecraft", "status_effect")
}
