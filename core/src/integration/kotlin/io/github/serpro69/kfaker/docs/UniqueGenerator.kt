package io.github.serpro69.kfaker.docs

import io.github.serpro69.kfaker.Faker
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue

@DisplayName("Snippets used in Orchid docs 'Unique Data Generator' wiki page")
class UniqueGenerator : DescribeSpec({
    describe("Unique Data Generator for Entire Data Provider") {
        it("should generate unique values for address provider") {
            // START unique_data_generator_one
            val faker = Faker()
            faker.unique.configuration { // ❶
                enable(faker::address) // ❷
            }
            val countries = List(60) { faker.address.country() } // ❸
            val cities = List(30) { faker.address.city() }
            val otherCities = List(30) { faker.address.city() } // ❹

            assertEquals(countries.distinct().size, 60)
            assertEquals(cities.distinct().size, 30)
            assertEquals(otherCities.distinct().size, 30)
            assertFalse(cities.any { otherCities.contains(it) }) // ❹
            // END unique_data_generator_one
        }

        it("should clear the record of generated values for a provider") {
            // START unique_data_generator_two
            val faker = Faker()
            faker.unique.configuration {
                enable(faker::address)
            }

            val countries = List(60) { faker.address.country() }
            faker.unique.clear(faker::address) // ❶
            val otherCountries = List(60) { faker.address.country() } // ❷

            assertTrue(countries.any { otherCountries.contains(it) })
            // END unique_data_generator_two
        }

        it("should clear all records of generated values") {
            // START unique_data_generator_three
            val faker = Faker()
            faker.unique.configuration {
                enable(faker::address)
                enable(faker::name)
            }
            // generate some values with 'address', 'name', and 'internet' providers

            // END unique_data_generator_three
            val countries = List(100) { faker.address.country() }
            val names = List(100) { faker.name.firstName() }

            // START unique_data_generator_four
            // clears records of generated values for 'address', 'name', and 'internet' providers
            faker.unique.clearAll()
            // END unique_data_generator_four

            val otherCountries = List(100) { faker.address.country() }
            val otherNames = List(100) { faker.name.firstName() }

            assertTrue(countries.any { otherCountries.contains(it) })
            assertTrue(names.any { otherNames.contains(it) })
        }

        it("should disable generation of unique values for a provider") {
            // START unique_data_generator_five
            val faker = Faker()
            faker.unique.configuration { // ❶
                enable(faker::address)
                enable(faker::name)
            }

            val countries = List(100) { faker.address.country() }
            val names = List(100) { faker.name.firstName() }

            faker.unique.configuration { disable(faker::address) } // ❷

            val otherCountries = List(100) { faker.address.country() }
            val otherNames = List(100) { faker.name.firstName() }

            assertTrue(countries.any { otherCountries.contains(it) }) // ❸
            assertFalse(names.any { otherNames.contains(it) }) // ❹
            // END unique_data_generator_five
        }

        it("should disable generation of unique values for all providers") {
            // START unique_data_generator_six
            val faker = Faker()
            faker.unique.configuration {
                enable(faker::address)
                enable(faker::name)
            }

            faker.unique.configuration { disableAll() } // ❶
            // END unique_data_generator_six

            val countries = List(100) { faker.address.country() }
            val names = List(100) { faker.name.firstName() }

            val otherCountries = List(100) { faker.address.country() }
            val otherNames = List(100) { faker.name.firstName() }

            assertTrue(countries.any { otherCountries.contains(it) })
            assertTrue(names.any { otherNames.contains(it) })
        }
    }

    describe("Unique Data Generator for Standalone Function") {
    }
})
