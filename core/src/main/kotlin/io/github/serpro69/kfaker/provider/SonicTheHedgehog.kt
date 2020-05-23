package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.GAMES] category.
 */
@Suppress("unused")
class SonicTheHedgehog internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<SonicTheHedgehog>(fakerService) {
    override val categoryName = CategoryName.GAMES
    override val localUniqueDataProvider = LocalUniqueDataProvider<SonicTheHedgehog>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun zone() = resolve("sonic_the_hedgehog", "zone")
    fun character() = resolve("sonic_the_hedgehog", "character")
    fun game() = resolve("sonic_the_hedgehog", "game")
}
