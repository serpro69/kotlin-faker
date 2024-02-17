@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package io.github.serpro69.kfaker

import io.github.serpro69.kfaker.provider.Address
import io.github.serpro69.kfaker.provider.Adjective
import io.github.serpro69.kfaker.provider.Airport
import io.github.serpro69.kfaker.provider.Ancient
import io.github.serpro69.kfaker.provider.Animal
import io.github.serpro69.kfaker.provider.Artist
import io.github.serpro69.kfaker.provider.Australia
import io.github.serpro69.kfaker.provider.Bird
import io.github.serpro69.kfaker.provider.Blood
import io.github.serpro69.kfaker.provider.Cat
import io.github.serpro69.kfaker.provider.Chiquito
import io.github.serpro69.kfaker.provider.ChuckNorris
import io.github.serpro69.kfaker.provider.Color
import io.github.serpro69.kfaker.provider.Currency
import io.github.serpro69.kfaker.provider.CurrencySymbol
import io.github.serpro69.kfaker.provider.Demographic
import io.github.serpro69.kfaker.provider.Dog
import io.github.serpro69.kfaker.provider.DrivingLicense
import io.github.serpro69.kfaker.provider.Educator
import io.github.serpro69.kfaker.provider.Emotion
import io.github.serpro69.kfaker.provider.File
import io.github.serpro69.kfaker.provider.FunnyName
import io.github.serpro69.kfaker.provider.Gender
import io.github.serpro69.kfaker.provider.GreekPhilosophers
import io.github.serpro69.kfaker.provider.Hipster
import io.github.serpro69.kfaker.provider.Hobby
import io.github.serpro69.kfaker.provider.Horse
import io.github.serpro69.kfaker.provider.IdNumber
import io.github.serpro69.kfaker.provider.Internet
import io.github.serpro69.kfaker.provider.JackHandey
import io.github.serpro69.kfaker.provider.Job
import io.github.serpro69.kfaker.provider.Lorem
import io.github.serpro69.kfaker.provider.Markdown
import io.github.serpro69.kfaker.provider.Measurement
import io.github.serpro69.kfaker.provider.Military
import io.github.serpro69.kfaker.provider.MitchHedberg
import io.github.serpro69.kfaker.provider.Money
import io.github.serpro69.kfaker.provider.Mountain
import io.github.serpro69.kfaker.provider.Name
import io.github.serpro69.kfaker.provider.Nation
import io.github.serpro69.kfaker.provider.NatoPhoneticAlphabet
import io.github.serpro69.kfaker.provider.Person
import io.github.serpro69.kfaker.provider.PhoneNumber
import io.github.serpro69.kfaker.provider.Quote
import io.github.serpro69.kfaker.provider.Rajnikanth
import io.github.serpro69.kfaker.provider.Relationship
import io.github.serpro69.kfaker.provider.Science
import io.github.serpro69.kfaker.provider.Separator
import io.github.serpro69.kfaker.provider.TrainStation
import io.github.serpro69.kfaker.provider.University
import io.github.serpro69.kfaker.provider.Verbs
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
class Faker @JvmOverloads constructor(config: FakerConfig = fakerConfig { }): AbstractFaker(config) {

    // misc providers
    val crypto: CryptographyProvider by lazy { CryptographyProvider(fakerService) }
    val random: RandomProvider by lazy { RandomProvider(fakerService) }
    // TODO rename to randomClass
    val randomProvider: RandomClassProvider by lazy { RandomClassProvider(config) }
    val string: StringProvider by lazy { StringProvider(fakerService) }

    val separator: Separator by lazy { Separator(fakerService) }
    val currencySymbol: CurrencySymbol by lazy { CurrencySymbol(fakerService) }

    // dictionary-based providers
    val address: Address by lazy { Address(fakerService) }
    val adjective: Adjective by lazy { Adjective(fakerService) }
    val airport: Airport by lazy { Airport(fakerService) }
    val ancient: Ancient by lazy { Ancient(fakerService) }
    val animal: Animal by lazy { Animal(fakerService) }
    val artist: Artist by lazy { Artist(fakerService) }
    val australia: Australia by lazy { Australia(fakerService) }
    val bird: Bird by lazy { Bird(fakerService) }
    val blood: Blood by lazy { Blood(fakerService) }
    val cat: Cat by lazy { Cat(fakerService) }
    // humor?
    val chiquito: Chiquito by lazy { Chiquito(fakerService) }
    val chuckNorris: ChuckNorris by lazy { ChuckNorris(fakerService) }
    //
    val color: Color by lazy { Color(fakerService) }

    //    val compass: Compass by lazy {Compass(fakerService) }
    val currency: Currency by lazy { Currency(fakerService) }
    val demographic: Demographic by lazy { Demographic(fakerService) }
    val dog: Dog by lazy { Dog(fakerService) }
    val drivingLicense: DrivingLicense by lazy { DrivingLicense(fakerService) }
    val educator: Educator by lazy { Educator(fakerService) }
    val emotion: Emotion by lazy { Emotion(fakerService) }
    val file: File by lazy { File(fakerService) }
    // humor?
    val funnyName: FunnyName by lazy { FunnyName(fakerService) }
    val gender: Gender by lazy { Gender(fakerService) }
    // books?
    val greekPhilosophers: GreekPhilosophers by lazy { GreekPhilosophers(fakerService) }
    val hipster: Hipster by lazy { Hipster(fakerService) }
    val hobby: Hobby by lazy { Hobby(fakerService) }
    val horse: Horse by lazy { Horse(fakerService) }
    val idNumber: IdNumber by lazy { IdNumber(fakerService) }
    val internet: Internet by lazy { Internet(fakerService, name) }
    // humor
    val jackHandey: JackHandey by lazy { JackHandey(fakerService) }
    val job: Job by lazy { Job(fakerService) }
    val lorem: Lorem by lazy { Lorem(fakerService) }
    val markdown: Markdown by lazy { Markdown(fakerService) }
    val measurement: Measurement by lazy { Measurement(fakerService) }
    val military: Military by lazy { Military(fakerService) }
    // humor
    val mitchHedberg: MitchHedberg by lazy { MitchHedberg(fakerService) }
    val money: Money by lazy { Money(fakerService) }
    val mountain: Mountain by lazy { Mountain(fakerService) }
    val name: Name by lazy { Name(fakerService) }
    val nation: Nation by lazy { Nation(fakerService) }
    val natoPhoneticAlphabet: NatoPhoneticAlphabet by lazy { NatoPhoneticAlphabet(fakerService) }
    val person: Person by lazy { Person(config.random) }
    val phoneNumber: PhoneNumber by lazy { PhoneNumber(fakerService) }
    val quote: Quote by lazy { Quote(fakerService) }
    val rajnikanth: Rajnikanth by lazy { Rajnikanth(fakerService) }
    val relationship: Relationship by lazy { Relationship(fakerService) }
    val science: Science by lazy { Science(fakerService) }

    //    val source: Source by lazy {Source(fakerService }
    val trainStation: TrainStation by lazy { TrainStation(fakerService) }
    val university: University by lazy { University(fakerService) }
    val verbs: Verbs by lazy { Verbs(fakerService) }

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
