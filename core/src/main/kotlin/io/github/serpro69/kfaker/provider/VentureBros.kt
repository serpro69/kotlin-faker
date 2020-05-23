package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.VENTURE_BROS] category.
 */
@Suppress("unused")
class VentureBros internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<VentureBros>(fakerService) {
    override val categoryName = CategoryName.VENTURE_BROS
    override val localUniqueDataProvider = LocalUniqueDataProvider<VentureBros>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun character() = resolve("character")
    fun organization() = resolve("organization")
    fun vehicle() = resolve("vehicle")
    fun quote() = resolve("quote")
}
