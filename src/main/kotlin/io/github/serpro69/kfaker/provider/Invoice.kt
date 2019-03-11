package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.INVOICE] category.
 */
class Invoice internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.INVOICE

    val checkDigitMethod = resolve { fakerService.resolve(Faker, it, "reference", "check_digit_method") }
    val pattern = resolve { fakerService.resolve(Faker, it, "reference", "pattern") }
}
