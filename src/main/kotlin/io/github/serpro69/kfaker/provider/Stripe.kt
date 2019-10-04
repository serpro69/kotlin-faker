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
        resolve("valid_cards", type)
    }
    val validTokens: (type: String) -> String = { type ->
        resolve("valid_tokens", type)
    }
    val invalidCards: (type: String) -> String = { type ->
        resolve("invalid_cards", type)
    }
}
