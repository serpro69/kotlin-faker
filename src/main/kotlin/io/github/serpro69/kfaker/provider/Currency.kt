package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.CURRENCY] category.
 */
@Suppress("unused")
class Currency internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Currency>(fakerService) {
    override val categoryName = CategoryName.CURRENCY
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val code = resolve("code")
    val name = resolve("name")
    val symbol = resolve("symbol")
}
