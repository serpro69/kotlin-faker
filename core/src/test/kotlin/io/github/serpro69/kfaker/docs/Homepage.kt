package io.github.serpro69.kfaker.docs

import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.fakerConfig
import io.kotest.core.spec.style.DescribeSpec
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.LocalDate

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
            // a total of 171 data providers
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
    }
})
