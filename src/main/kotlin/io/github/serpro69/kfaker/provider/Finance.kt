package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.FINANCE] category.
 */
@Suppress("unused")
class Finance internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Finance>(fakerService) {
    override val categoryName = CategoryName.FINANCE
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun creditCard(type: String) = resolve("credit_card", type)

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun vatNumber(countryCode: String) = resolve("vat_number", countryCode)
}
