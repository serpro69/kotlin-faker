package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.COLOR] category.
 */
@Suppress("unused")
class Color internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Color>(fakerService) {
    override val categoryName = CategoryName.COLOR
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val name = resolve("name")
}
