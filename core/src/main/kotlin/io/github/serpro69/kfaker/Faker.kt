@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package io.github.serpro69.kfaker

import io.github.serpro69.kfaker.provider.Address
import io.github.serpro69.kfaker.provider.Ancient
import io.github.serpro69.kfaker.provider.Animal
import io.github.serpro69.kfaker.provider.Artist
import io.github.serpro69.kfaker.provider.Bird
import io.github.serpro69.kfaker.provider.Blood
import io.github.serpro69.kfaker.provider.Cat
import io.github.serpro69.kfaker.provider.Color
import io.github.serpro69.kfaker.provider.Currency
import io.github.serpro69.kfaker.provider.CurrencySymbol
import io.github.serpro69.kfaker.provider.Demographic
import io.github.serpro69.kfaker.provider.Dog
import io.github.serpro69.kfaker.provider.DrivingLicense
import io.github.serpro69.kfaker.provider.Emotion
import io.github.serpro69.kfaker.provider.File
import io.github.serpro69.kfaker.provider.Gender
import io.github.serpro69.kfaker.provider.GreekPhilosophers
import io.github.serpro69.kfaker.provider.Hipster
import io.github.serpro69.kfaker.provider.Hobby
import io.github.serpro69.kfaker.provider.Horse
import io.github.serpro69.kfaker.provider.IdNumber
import io.github.serpro69.kfaker.provider.Internet
import io.github.serpro69.kfaker.provider.Measurement
import io.github.serpro69.kfaker.provider.Military
import io.github.serpro69.kfaker.provider.Money
import io.github.serpro69.kfaker.provider.Name
import io.github.serpro69.kfaker.provider.Person
import io.github.serpro69.kfaker.provider.PhoneNumber
import io.github.serpro69.kfaker.provider.Quote
import io.github.serpro69.kfaker.provider.Rajnikanth
import io.github.serpro69.kfaker.provider.Relationship
import io.github.serpro69.kfaker.provider.Separator
import io.github.serpro69.kfaker.provider.misc.CryptographyProvider
import io.github.serpro69.kfaker.provider.misc.RandomClassProvider
import io.github.serpro69.kfaker.provider.misc.RandomProvider
import io.github.serpro69.kfaker.provider.misc.StringProvider

/**
 * Provides access to fake data generators.
 *
 * Each category (generator) from this [Faker] is represented by a property that has the same name as the `.yml` file.
 *
 * @property random provides public access to the functions of [RandomService].
 * @property randomProvider provides additional functionality that is not covered by other data providers
 * such as [address], [name], [internet], and so on. See [RandomClassProvider] for more details.
 * @property string provides functionality to generate strings from expressions/templates
 * @property unique global provider for generation of unique values.
 */
@Suppress("unused")
class Faker @JvmOverloads constructor(config: FakerConfig = fakerConfig { }) : AbstractFaker(config) {

    // misc providers
    val crypto: CryptographyProvider by lazy { CryptographyProvider(fakerService) }
    val random: RandomProvider by lazy { RandomProvider(fakerService) }
    val randomClass: RandomClassProvider by lazy { RandomClassProvider(config) }
    val string: StringProvider by lazy { StringProvider(fakerService) }

    @Deprecated(
        message = "This property is deprecated and will be removed in future releases",
        level = DeprecationLevel.WARNING,
        replaceWith = ReplaceWith("randomClass")
    )
    val randomProvider: RandomClassProvider by lazy { RandomClassProvider(config) }

    val separator: Separator by lazy { Separator(fakerService) }
    val currencySymbol: CurrencySymbol by lazy { CurrencySymbol(fakerService) }

    // dictionary-based providers
    val address: Address by lazy { Address(fakerService) }

    // creature
    val ancient: Ancient by lazy { Ancient(fakerService) }

    // creature
    val animal: Animal by lazy { Animal(fakerService) }
    val artist: Artist by lazy { Artist(fakerService) }

    // creature
    val bird: Bird by lazy { Bird(fakerService) }
    val blood: Blood by lazy { Blood(fakerService) }

    // creature
    val cat: Cat by lazy { Cat(fakerService) }
    val color: Color by lazy { Color(fakerService) }
    val currency: Currency by lazy { Currency(fakerService) }

    // misc
    val demographic: Demographic by lazy { Demographic(fakerService) }

    // creature
    val dog: Dog by lazy { Dog(fakerService) }

    // misc
    val drivingLicense: DrivingLicense by lazy { DrivingLicense(fakerService) }

    // lorem
    val emotion: Emotion by lazy { Emotion(fakerService) }
    val file: File by lazy { File(fakerService) }
    val gender: Gender by lazy { Gender(fakerService) }

    // books
    val greekPhilosophers: GreekPhilosophers by lazy { GreekPhilosophers(fakerService) }

    // lorem
    val hipster: Hipster by lazy { Hipster(fakerService) }

    // lorem
    val hobby: Hobby by lazy { Hobby(fakerService) }

    // creature
    val horse: Horse by lazy { Horse(fakerService) }
    val idNumber: IdNumber by lazy { IdNumber(fakerService) }
    val internet: Internet by lazy { Internet(fakerService, name) }

    val measurement: Measurement by lazy { Measurement(fakerService) }

    // misc
    val military: Military by lazy { Military(fakerService) }
    val money: Money by lazy { Money(fakerService) }
    val name: Name by lazy { Name(fakerService) }
    val person: Person by lazy { Person(config.random) }
    val phoneNumber: PhoneNumber by lazy { PhoneNumber(fakerService) }

    // lorem
    val quote: Quote by lazy { Quote(fakerService) }

    // movie
    val rajnikanth: Rajnikanth by lazy { Rajnikanth(fakerService) }

    // misc
    val relationship: Relationship by lazy { Relationship(fakerService) }

    //    val source: Source by lazy { Source(fakerService) }

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
 * Applies the [block] function to [Faker.Builder]
 * and returns as an instance of [Faker] from that builder.
 */
fun faker(block: Faker.Builder.() -> Unit): Faker = Faker.Builder().apply(block).build()
