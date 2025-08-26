@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package io.github.serpro69.kfaker

import io.github.serpro69.kfaker.provider.Address
import io.github.serpro69.kfaker.provider.Color
import io.github.serpro69.kfaker.provider.Currency
import io.github.serpro69.kfaker.provider.CurrencySymbol
import io.github.serpro69.kfaker.provider.File
import io.github.serpro69.kfaker.provider.Gender
import io.github.serpro69.kfaker.provider.IdNumber
import io.github.serpro69.kfaker.provider.Internet
import io.github.serpro69.kfaker.provider.Measurement
import io.github.serpro69.kfaker.provider.Money
import io.github.serpro69.kfaker.provider.Name
import io.github.serpro69.kfaker.provider.Person
import io.github.serpro69.kfaker.provider.PhoneNumber
import io.github.serpro69.kfaker.provider.Separator
import io.github.serpro69.kfaker.provider.misc.CryptographyProvider
import io.github.serpro69.kfaker.provider.misc.RandomClassProvider
import io.github.serpro69.kfaker.provider.misc.RandomProvider
import io.github.serpro69.kfaker.provider.misc.StringProvider

/**
 * Provides access to 'core' fake data generators.
 *
 * Each category (generator) from this [Faker] is represented by a property that has the same name
 * as the `.yml` file.
 *
 * @property random provides data-generator-like functionality for the functions of [RandomService].
 * @property randomClass provides additional functionality that is not covered by other data
 *   providers such as [address], [name], [internet], and so on. See [RandomClassProvider] for more
 *   details.
 * @property string provides functionality to generate strings from expressions/templates
 * @property unique global provider for generation of unique values.
 */
@Suppress("unused")
class Faker @JvmOverloads constructor(config: FakerConfig = fakerConfig {}) :
    AbstractFaker(config) {

    // misc providers
    val crypto: CryptographyProvider by lazy { CryptographyProvider(fakerService) }
    val random: RandomProvider by lazy { RandomProvider(fakerService) }
    val randomClass: RandomClassProvider by lazy { RandomClassProvider(config) }
    val string: StringProvider by lazy { StringProvider(fakerService) }

    // yml dictionary-based providers
    val address: Address by lazy { Address(fakerService) }
    val color: Color by lazy { Color(fakerService) }
    val currency: Currency by lazy { Currency(fakerService) }
    val currencySymbol: CurrencySymbol by lazy { CurrencySymbol(fakerService) }
    val file: File by lazy { File(fakerService) }
    val gender: Gender by lazy { Gender(fakerService) }
    val idNumber: IdNumber by lazy { IdNumber(fakerService) }
    val internet: Internet by lazy { Internet(fakerService, name) }
    val measurement: Measurement by lazy { Measurement(fakerService) }
    val money: Money by lazy { Money(fakerService) }
    val name: Name by lazy { Name(fakerService) }
    val person: Person by lazy { Person(config.random) }
    val phoneNumber: PhoneNumber by lazy { PhoneNumber(fakerService) }
    val separator: Separator by lazy { Separator(fakerService) }

    // TODO: val source: Source by lazy { Source(fakerService) }

    @FakerDsl
    /** DSL builder for creating instances of [Faker] */
    class Builder internal constructor() : AbstractFaker.Builder<Faker>() {

        /** Builds an instance of [Faker] with this [config]. */
        override fun build(): Faker = Faker(config)
    }
}

/**
 * Applies the [block] function to [Faker.Builder] and returns as an instance of [Faker] from that
 * builder.
 */
fun faker(block: Faker.Builder.() -> Unit): Faker = Faker.Builder().apply(block).build()
