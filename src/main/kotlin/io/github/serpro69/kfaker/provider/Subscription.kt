package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.SUBSCRIPTION] category.
 */
@Suppress("unused")
class Subscription internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Subscription>(fakerService) {
    override val categoryName = CategoryName.SUBSCRIPTION
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val plans = resolve("plans")
    val statuses = resolve("statuses")
    val paymentMethods = resolve("payment_methods")
    val subscriptionTerms = resolve("subscription_terms")
    val paymentTerms = resolve("payment_terms")
}
