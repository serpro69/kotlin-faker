package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.VENTURE_BROS] category.
 */
@Suppress("unused")
class VentureBros internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<VentureBros>(fakerService) {
    override val categoryName = CategoryName.VENTURE_BROS
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val character = resolve("character")
    val organization = resolve("organization")
    val vehicle = resolve("vehicle")
    val quote = resolve("quote")
}
