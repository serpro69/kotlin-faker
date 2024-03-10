@file:Suppress("unused", "UNUSED_VARIABLE", "RemoveExplicitTypeArguments")

package io.github.serpro69.kfaker.docs

import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.fakerConfig
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import java.util.*

/**
 * Documentation code snippets for the docs website homepage.
 *
 * This approach has the benefit over using something like code-blocks in .md files directly in a way
 * that this becomes a "live-documentation",
 * and additionally it's also being tested since this is, after all, a test class.
 *
 * Usage:
 * ❶ Wrap each code snippet in comments like `// START snippet_name` and `// END snippet_name`
 * ❷ Code snippets can be referenced from the docs using the {% snippet snippet_name %} tag.
 * ❸ (See https://orchid.run/plugins/orchidsnippets for snippets docs)
 */
@DisplayName("Snippets used in Orchid docs 'homepage'")
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
            it("should print a safe email address") {
                // START data_provider_three
                faker.internet.safeEmail() // => chance.marvin@yahoo.test
                // END data_provider_three
            }
            // START data_provider_seven
            // a total of 213 different data providers
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
            it("should generate unique values in Address category") {
                // START unique_data_zero
                val faker = Faker()
                // END unique_data_zero

                // START unique_data_one
                faker.unique.configuration {
                    // enable generation of unique values for address data provider
                    enable(faker::address)
                }
                val countries = List(100) { faker.address.country() }
                assertEquals(countries.distinct().size, 100)
                // END unique_data_one
            }
            it("should generate unique countries, but not unique cities") {
                val faker = Faker()
                // START unique_data_two
                val countries = List(100) { faker.address.unique.country() }
                val cities = List(1000) { faker.address.city() }
                assertEquals(countries.distinct().size, 100)
                assertTrue(cities.distinct().size < 1000)
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
                fun randomString() = "X3a8s813dcb";
                // START random_class_instance_two
                class Baz(val id: Int, val uuid: UUID, val username: String)

                val baz: Baz = faker.randomProvider.randomClassInstance {
                    // ヽ(^o^)丿 ᕕ(ᐛ)ᕗ Prepend string type parameter values with parameter name!
                    typeGenerator<String> { parameterInfo -> "${parameterInfo.name}_${randomString()}" }
                    typeGenerator<UUID> { UUID.fromString("00000000-0000-0000-0000-000000000000") }
                    typeGenerator<Int> { faker.random.nextInt(min = 0, max = 9) }
                }
                assertEquals(baz.username, "username_X3a8s813dcb")
                assertEquals(baz.uuid, UUID.fromString("00000000-0000-0000-0000-000000000000"))
                assertTrue(baz.id in 0..9)
                // END random_class_instance_two
            }
        }

        context("Faker.random") {
            val faker = Faker()
            it("should generate random stuff") {
                // START random_service_one
                faker.random.nextInt(intRange = 0..1000)
                faker.random.nextLong(bound = 999L)
                faker.random.randomString(length = 99)
                faker.random.nextEnum<TestEnum>()
                faker.random.nextEnum(TestEnum::class.java) {
                    it != TestEnum.SOME // Exclude 'SOME' enum
                }
                faker.random.nextUUID()
                // END random_service_one
            }
        }

        context("StringProvider") {
            val faker = Faker()
            it("should generate random stuff") {
                // START string_provider_one
                faker.string.numerify("foo###bar") // foo123bar
                faker.string.letterify("foo???bar", true) // fooXYZbar
                faker.string.bothify("foo?##bar", false) // foox42bar
                faker.string.regexify("""\d{2}\w""") // 42a
                // END string_provider_one
            }
        }
    }
})

enum class TestEnum {
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SOME
}
