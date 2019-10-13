package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.THE_THICK_OF_IT] category.
 */
@Suppress("unused")
class TheThickOfIt internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<TheThickOfIt>(fakerService) {
    override val categoryName = CategoryName.THE_THICK_OF_IT
    override val uniqueDataProvider = UniqueDataProvider<TheThickOfIt>()
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    fun characters() = resolve("characters")
    fun positions() = resolve("positions")
    fun departments() = resolve("departments")
}
