package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.BUSINESS] category.
 */
class Business internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.BUSINESS

    val creditCardNumbers = resolve { fakerService.resolve(it, "credit_card_numbers") }
    val creditCardTypes = resolve { fakerService.resolve(it, "credit_card_types") }
}
