package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.SWORD_ART_ONLINE] category.
 */
@Suppress("unused")
class SwordArtOnline internal constructor(fakerService: FakerService) : YamlFakeDataProvider<SwordArtOnline>(fakerService) {
    override val yamlCategory = YamlCategory.SWORD_ART_ONLINE
    override val localUniqueDataProvider = LocalUniqueDataProvider<SwordArtOnline>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun realName() = resolve("real_name")
    fun gameName() = resolve("game_name")
    fun location() = resolve("location")
    fun item() = resolve("item")
}
