package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.COMMUNITY] category.
 */
@Suppress("unused")
class Community internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Community>(fakerService) {
    override val categoryName = CategoryName.COMMUNITY
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val characters = resolve("characters")
    val quotes = resolve("quotes")
}
