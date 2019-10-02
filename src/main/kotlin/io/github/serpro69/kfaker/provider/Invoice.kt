package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.INVOICE] category.
 */
@Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
@Suppress("unused")
class Invoice internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.INVOICE

    val checkDigitMethod = resolve { fakerService.resolve(it, "reference", "check_digit_method") }
    val pattern = resolve { fakerService.resolve(it, "reference", "pattern") }
}
