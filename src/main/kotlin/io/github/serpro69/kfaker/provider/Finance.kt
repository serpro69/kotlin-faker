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
    val creditCard: (type: String) -> String = { type -> resolve("credit_card", type) }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val vatNumber: (countryCode: String) -> String = { countryCode ->
        resolve("vat_number", countryCode)
    }
}
