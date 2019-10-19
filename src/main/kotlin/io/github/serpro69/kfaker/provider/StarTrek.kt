package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.STAR_TREK] category.
 */
@Suppress("unused")
class StarTrek internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<StarTrek>(fakerService) {
    override val categoryName = CategoryName.STAR_TREK
    override val localUniqueDataProvider = LocalUniqueDataProvider<StarTrek>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun character() = resolve("character")
    fun location() = resolve("location")
    fun specie() = resolve("specie")
    fun villain() = resolve("villain")
}
