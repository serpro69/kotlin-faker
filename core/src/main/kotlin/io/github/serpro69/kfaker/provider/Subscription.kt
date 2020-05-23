package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.SUBSCRIPTION] category.
 */
@Suppress("unused")
class Subscription internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Subscription>(fakerService) {
    override val categoryName = CategoryName.SUBSCRIPTION
    override val localUniqueDataProvider = LocalUniqueDataProvider<Subscription>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun plans() = resolve("plans")
    fun statuses() = resolve("statuses")
    fun paymentMethods() = resolve("payment_methods")
    fun subscriptionTerms() = resolve("subscription_terms")
    fun paymentTerms() = resolve("payment_terms")
}
