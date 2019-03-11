package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.STRIPE] category.
 */
class Stripe internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.STRIPE

    val validCards: (type: String) -> String = { type ->
        resolve { fakerService.resolve(Faker, it, "valid_cards", type) }.invoke()
    }
    val validTokens: (type: String) -> String = { type ->
        resolve { fakerService.resolve(Faker, it, "valid_tokens", type) }.invoke()
    }
    val invalidCards: (type: String) -> String = { type ->
        resolve { fakerService.resolve(Faker, it, "invalid_cards", type) }.invoke()
    }
}
