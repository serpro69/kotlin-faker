package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.GAMES] category.
 */
@Suppress("unused")
class HalfLife internal constructor(fakerService: FakerService) : YamlFakeDataProvider<HalfLife>(fakerService) {
    override val yamlCategory = YamlCategory.GAMES
    override val secondaryCategory: Category = Category.ofName("HALF_LIFE")
    override val localUniqueDataProvider = LocalUniqueDataProvider<HalfLife>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory, secondaryCategory)
    }

    fun character() = resolve(secondaryCategory, "character")
    fun enemy() = resolve(secondaryCategory, "enemy")
    fun location() = resolve(secondaryCategory, "location")
}
