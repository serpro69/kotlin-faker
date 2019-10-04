package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.SOUTH_PARK] category.
 */
@Suppress("unused")
class SouthPark internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<SouthPark>(fakerService) {
    override val categoryName = CategoryName.SOUTH_PARK
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val characters = resolve("characters")
    val quotes = resolve("quotes")
}
