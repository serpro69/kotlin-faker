package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.INVOICE] category.
 */
@Suppress("unused")
class Invoice internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Invoice>(fakerService) {
    override val categoryName = CategoryName.INVOICE
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val checkDigitMethod = resolve("reference", "check_digit_method")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val pattern = resolve("reference", "pattern")
}
