package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.BUSINESS] category.
 */
@Suppress("unused")
class Business internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Business>(fakerService) {
    override val categoryName = CategoryName.BUSINESS
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val creditCardNumbers = resolve("credit_card_numbers")
    val creditCardTypes = resolve("credit_card_types")
}
