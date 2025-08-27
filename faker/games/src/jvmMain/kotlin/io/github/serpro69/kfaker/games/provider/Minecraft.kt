package io.github.serpro69.kfaker.games.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.Category
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/** [FakeDataProvider] implementation for [YamlCategory.GAMES] category. */
@Suppress("unused")
class Minecraft internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<Minecraft>(fakerService) {
    override val yamlCategory = YamlCategory.GAMES
    override val secondaryCategory: Category = Category.ofName("MINECRAFT")
    override val localUniqueDataProvider = LocalUniqueDataProvider<Minecraft>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory, secondaryCategory)
    }

    fun achievement() = resolve(secondaryCategory, "achievement")

    fun biome() = resolve(secondaryCategory, "biome")

    fun blocks() = resolve(secondaryCategory, "blocks")

    fun enchantment() = resolve(secondaryCategory, "enchantment")

    fun gameMode() = resolve(secondaryCategory, "game_mode")

    fun items() = resolve(secondaryCategory, "items")

    fun mobs() = resolve(secondaryCategory, "mobs")

    fun statusEffect() = resolve(secondaryCategory, "status_effect")
}
