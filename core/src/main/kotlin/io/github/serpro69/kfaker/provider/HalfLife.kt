package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.GAMES] category.
 */
@Suppress("unused")
class HalfLife internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<HalfLife>(fakerService) {
    override val category = YamlCategory.GAMES
    override val localUniqueDataProvider = LocalUniqueDataProvider<HalfLife>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun character() = resolve("half_life", "character")
    fun enemy() = resolve("half_life", "enemy")
    fun location() = resolve("half_life", "location")
}
