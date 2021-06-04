@file:Suppress("unused", "UNUSED_VARIABLE", "RemoveExplicitTypeArguments")

package io.github.serpro69.kfaker.docs

import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.TestEnum
import io.github.serpro69.kfaker.fakerConfig
import io.kotest.core.spec.style.DescribeSpec
import org.junit.jupiter.api.DisplayName
import java.util.*

// Wrap each code snippet in comments like "// START (snippet name)...// END (snippet name)"
// Code snippets can be referenced from the docs using the {% snippet %} tag
// (see https://orchid.run/plugins/orchidsnippets for snippets docs)
class Homepage : DescribeSpec({
    describe("Faker") {
        context("Fake Data Provider") {
            // START data_provider_zero
            val faker = Faker()
            // END data_provider_zero

            it("should print a person's name with middle") {
                // START data_provider_one
                faker.name.nameWithMiddle() // => Dr. John Abrams Smith
                // END data_provider_one
            }
            it("should print an address") {
                // START data_provider_two
                faker.address.streetAddress() // => 474 Kilback Manor
                // END data_provider_two
            }
            it("should print a SWIFT BIC code") {
                // START data_provider_four
                faker.bank.swiftBic() // => AACCGB21
                // END data_provider_four
            }
            it("should print a safe email address") {
                // START data_provider_three
                faker.internet.safeEmail() // => chance.marvin@yahoo.test
                // END data_provider_three
            }
            it("should print a ship name from The Expanse") {
                // START data_provider_five
                faker.theExpanse.ships() // => Rocinante
                // END data_provider_five
            }
            it("should print a Star Wars character name") {
                // START data_provider_six_a
                faker.starWars.characters() // => Darth Vader
                // END data_provider_six_a
            }
            it("should print a character name from Friends") {
                // START data_provider_six_b
                faker.friends.characters() // => Phoebe Buffay
                // END data_provider_six_b
            }
            // START data_provider_seven
            // a total of 171 different data providers
            // END data_provider_seven
        }

        context("Faker Locale") {
            // START faker_locale_zero
            val config = fakerConfig { locale = "nb-NO" }
            val faker = Faker(config)
            // END faker_locale_zero

            it("should print a Norwegian city name") {
                // START faker_locale_one
                faker.address.city() // => Høyberg
                // END faker_locale_one
            }
            it("should print a Norwegian full name with middle") {
                // START faker_locale_two
                faker.name.nameWithMiddle() // => Sofie Ødegård Løken
                // END faker_locale_two
            }
        }

        context("Unique data generation") {
            // START unique_data_zero
            val faker = Faker()
            // END unique_data_zero
            it("should generate unique values in Address category") {
                // START unique_data_one
                faker.unique.configuration {
                    // enable generation of unique values for address data provider
                    enable(faker::address)
                }
                val countries = List(100) { faker.address.country() }
                assert(countries.distinct().size == 100)
                // END unique_data_one
            }
            it("should generate unique countries, but not unique cities") {
                // START unique_data_two
                val countries = List(100) { faker.address.unique.country() }
                val cities = List(100) { faker.address.city() }
                assert(countries.distinct().size == 100)
                assert(cities.distinct().size < 100)
                // END unique_data_two
            }
        }

        context("RandomProvider") {
            // START and_more_zero
            val faker = Faker()
            // END and_more_zero

            it("should generate random instance of a class") {
                // START random_class_instance_one
                class Foo(val a: String)
                class Bar(val foo: Foo)
                val foo: Foo = faker.randomProvider.randomClassInstance()
                val bar: Bar = faker.randomProvider.randomClassInstance()
                // END random_class_instance_one
            }
            it("should generate types by configuration") {
                // START random_class_instance_two
                class Baz(val id: Int, val uuid: UUID, val name: String)
                val baz: Baz = faker.randomProvider.randomClassInstance {
                    typeGenerator<UUID> { UUID.fromString("00000000-0000-0000-0000-000000000000") }
                    typeGenerator<Int> { faker.random.nextInt(min = 0, max = 9)  }
                    typeGenerator<String> { faker.name.name() } // Random name generated by Faker
                }
                assert(baz.uuid == UUID.fromString("00000000-0000-0000-0000-000000000000"))
                assert(baz.id in 0 until 9)
                // END random_class_instance_two
            }
        }

        context("Faker.random") {
            val faker = Faker()
            it("should generate random stuff") {
                // START random_service_one
                faker.random.nextInt(intRange = 0..1000)
                faker.random.nextLong(bound = 999L)
                faker.random.nextString(length = 99)
                faker.random.nextEnum<TestEnum>()
                faker.random.nextEnum(TestEnum::class.java) {
                    it != TestEnum.SOME // Exclude 'SOME' enum
                }
                faker.random.nextUUID()
                // END random_service_one
            }
        }
    }
})

private const val cli_snippet = """
    // START cli_app_zero
    faker-bot lookup name --verbose
    #Faker()
    #├── address
    #│   ├── cityName() // => Port Olinstad
    #│   ├── countryByName() // => NR
    #│   └── streetName() // => Lance Roads
    #├── animal
    #│   └── name() // => gnat
    #├── app
    #│   └── name() // => Bamity
    #├── artist
    #│   └── names() // => Cezanne
    #├── bank
    #│   └── name() // => ABN AMRO HOARE GOVETT SECURITIES
    #├── beer
    #│   └── name() // => Oaked Arrogant Bastard Ale
    # rest of output that matches query
    // END cli_app_zero
"""
