package io.github.serpro69.kfaker.commerce

import io.github.serpro69.kfaker.AbstractFaker
import io.github.serpro69.kfaker.FakerConfig
import io.github.serpro69.kfaker.FakerDsl
import io.github.serpro69.kfaker.commerce.provider.Bank
import io.github.serpro69.kfaker.commerce.provider.Barcode
import io.github.serpro69.kfaker.commerce.provider.Beer
import io.github.serpro69.kfaker.commerce.provider.Business
import io.github.serpro69.kfaker.commerce.provider.Cannabis
import io.github.serpro69.kfaker.commerce.provider.Code
import io.github.serpro69.kfaker.commerce.provider.Coffee
import io.github.serpro69.kfaker.commerce.provider.Commerce
import io.github.serpro69.kfaker.commerce.provider.Company
import io.github.serpro69.kfaker.commerce.provider.Construction
import io.github.serpro69.kfaker.commerce.provider.Dessert
import io.github.serpro69.kfaker.commerce.provider.Finance
import io.github.serpro69.kfaker.commerce.provider.Food
import io.github.serpro69.kfaker.commerce.provider.IndustrySegments
import io.github.serpro69.kfaker.commerce.provider.Marketing
import io.github.serpro69.kfaker.commerce.provider.Restaurant
import io.github.serpro69.kfaker.commerce.provider.Stripe
import io.github.serpro69.kfaker.commerce.provider.Subscription
import io.github.serpro69.kfaker.commerce.provider.Tea
import io.github.serpro69.kfaker.fakerConfig

/**
 * Typealias for the [CommerceFaker]
 */
typealias Faker = CommerceFaker

/**
 * Provides access to fake data generators within the Commerce domain.
 *
 * Each category (generator) from this [CommerceFaker] is represented by a property
 * that (usually) has the same name as the `.yml` dictionary file.
 *
 * @property unique global provider for generation of unique values.
 */
@Suppress("unused")
class CommerceFaker @JvmOverloads constructor(config: FakerConfig = fakerConfig { }) : AbstractFaker(config) {

    val bank: Bank by lazy { Bank(fakerService) }
    val barcode: Barcode by lazy { Barcode(fakerService) }
    val beer: Beer by lazy { Beer(fakerService) }
    val business: Business by lazy { Business(fakerService) }
    val cannabis: Cannabis by lazy { Cannabis(fakerService) }
    val code: Code by lazy { Code(fakerService) }
    val coffee: Coffee by lazy { Coffee(fakerService) }
    val commerce: Commerce by lazy { Commerce(fakerService) }
    val company: Company by lazy { Company(fakerService) }
    val construction: Construction by lazy { Construction(fakerService) }
    val dessert: Dessert by lazy { Dessert(fakerService) }
    val finance: Finance by lazy { Finance(fakerService, randomService) }
    val food: Food by lazy { Food(fakerService) }
    val industrySegments: IndustrySegments by lazy { IndustrySegments(fakerService) }
    // TODO not implemented
    //  val invoice: Invoice by lazy { Invoice(fakerService }
    val marketing: Marketing by lazy { Marketing(fakerService) }
    val restaurant: Restaurant by lazy { Restaurant(fakerService) }
    val stripe: Stripe by lazy { Stripe(fakerService) }
    val subscription: Subscription by lazy { Subscription(fakerService) }
    val tea: Tea by lazy { Tea(fakerService) }

    @FakerDsl
    /**
     * DSL builder for creating instances of [Faker]
     */
    class Builder internal constructor() : AbstractFaker.Builder<Faker>() {

        /**
         * Builds an instance of [Faker] with this [config].
         */
        override fun build(): Faker = Faker(config)
    }
}

/**
 * Applies the [block] function to [CommerceFaker.Builder]
 * and returns as an instance of [CommerceFaker] from that builder.
 */
fun faker(block: CommerceFaker.Builder.() -> Unit): CommerceFaker = CommerceFaker.Builder().apply(block).build()
