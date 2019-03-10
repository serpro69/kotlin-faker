package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.SUBSCRIPTION] category.
 */
class Subscription internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.SUBSCRIPTION

    val plans = resolve { fakerService.resolve(Faker, it, "plans") }
    val statuses = resolve { fakerService.resolve(Faker, it, "statuses") }
    val paymentMethods = resolve { fakerService.resolve(Faker, it, "payment_methods") }
    val subscriptionTerms = resolve { fakerService.resolve(Faker, it, "subscription_terms") }
    val paymentTerms = resolve { fakerService.resolve(Faker, it, "payment_terms") }
}
