package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.BUSINESS] category.
 */
class Business internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.BUSINESS

    val creditCardNumbers = resolve { fakerService.resolve(Faker, it, "credit_card_numbers") }
    val creditCardTypes = resolve { fakerService.resolve(Faker, it, "credit_card_types") }
}
