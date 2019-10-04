package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.STAR_TREK] category.
 */
@Suppress("unused")
class StarTrek internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<StarTrek>(fakerService) {
    override val categoryName = CategoryName.STAR_TREK
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val character = resolve("character")
    val location = resolve("location")
    val specie = resolve("specie")
    val villain = resolve("villain")
}
