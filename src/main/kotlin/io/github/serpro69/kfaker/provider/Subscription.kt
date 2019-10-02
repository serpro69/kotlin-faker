package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.SUBSCRIPTION] category.
 */
@Suppress("unused")
class Subscription internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.SUBSCRIPTION

    val plans = resolve { fakerService.resolve(it, "plans") }
    val statuses = resolve { fakerService.resolve(it, "statuses") }
    val paymentMethods = resolve { fakerService.resolve(it, "payment_methods") }
    val subscriptionTerms = resolve { fakerService.resolve(it, "subscription_terms") }
    val paymentTerms = resolve { fakerService.resolve(it, "payment_terms") }
}
