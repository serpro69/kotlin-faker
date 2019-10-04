package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.SCIENCE] category.
 */
@Suppress("unused")
class Science internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Science>(fakerService) {
    override val categoryName = CategoryName.SCIENCE
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val element = resolve("element")
    val elementSymbol = resolve("element_symbol")
    val scientist = resolve("scientist")
}
