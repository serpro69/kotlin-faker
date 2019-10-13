package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.BUSINESS] category.
 */
@Suppress("unused")
class Business internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Business>(fakerService) {
    override val categoryName = CategoryName.BUSINESS
    override val uniqueDataProvider = UniqueDataProvider<Business>()
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    fun creditCardNumbers() = resolve("credit_card_numbers")
    fun creditCardTypes() = resolve("credit_card_types")
}
