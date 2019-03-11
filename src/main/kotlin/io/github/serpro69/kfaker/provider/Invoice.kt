package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.INVOICE] category.
 */
class Invoice internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.INVOICE

    val checkDigitMethod = resolve { fakerService.resolve(Faker, it, "reference", "check_digit_method") }
    val pattern = resolve { fakerService.resolve(Faker, it, "reference", "pattern") }
}
