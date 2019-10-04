package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.STRIPE] category.
 */
@Suppress("unused")
class Stripe internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Stripe>(fakerService) {
    override val categoryName = CategoryName.STRIPE
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val validCards: (type: String) -> String = { type ->
        resolve { fakerService.resolve(it, "valid_cards", type) }.invoke()
    }
    val validTokens: (type: String) -> String = { type ->
        resolve { fakerService.resolve(it, "valid_tokens", type) }.invoke()
    }
    val invalidCards: (type: String) -> String = { type ->
        resolve { fakerService.resolve(it, "invalid_cards", type) }.invoke()
    }
}
