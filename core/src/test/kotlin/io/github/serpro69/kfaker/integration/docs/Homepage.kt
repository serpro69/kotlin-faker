@file:Suppress("unused", "UNUSED_VARIABLE", "RemoveExplicitTypeArguments")

package io.github.serpro69.kfaker.integration.docs

import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.fakerConfig
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import java.util.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue

/**
 * Documentation code snippets for the docs website homepage.
 *
 * This approach has the benefit over using something like code-blocks in .md files directly in a
 * way that this becomes a "live-documentation", and additionally it's also being tested since this
 * is, after all, a test class.
 *
 * Usage: ❶ Wrap each code snippet in comments like `// --8<-- [start:snippet_name]` and `// --8<--
 * [end:snippet_name`] ❷ Code snippets can be referenced from the docs using the {% snippet
 * snippet_name %} tag. ❸ (See https://orchid.run/plugins/orchidsnippets for snippets docs)
 */
@DisplayName("Snippets used in Orchid docs 'homepage'")
class Homepage :
    DescribeSpec({
        describe("Faker") {
            context("Fake Data Provider") {
                // --8<-- [start:data_provider_zero]
                val faker = Faker()
                // --8<-- [end:data_provider_zero]

                it("should print a person's name with middle") {
                    // --8<-- [start:data_provider_one]
                    faker.name.nameWithMiddle() // => Dr. John Abrams Smith
                    // --8<-- [end:data_provider_one]
                }
                it("should print an address") {
                    // --8<-- [start:data_provider_two]
                    faker.address.streetAddress() // => 474 Kilback Manor
                    // --8<-- [end:data_provider_two]
                }
                it("should print a safe email address") {
                    // --8<-- [start:data_provider_three]
                    faker.internet.safeEmail() // => chance.marvin@yahoo.test
                    // --8<-- [end:data_provider_three]
                }
                // --8<-- [start:data_provider_seven]
                // a total of 213 different data generators
                // --8<-- [end:data_provider_seven]
            }

            context("Faker Locale") {
                // --8<-- [start:faker_locale_zero]
                val config = fakerConfig { locale = "nb-NO" }
                val faker = Faker(config)
                // --8<-- [end:faker_locale_zero]

                it("should print a Norwegian city name") {
                    // --8<-- [start:faker_locale_one]
                    faker.address.city() // => Høyberg
                    // --8<-- [end:faker_locale_one]
                }
                it("should print a Norwegian full name with middle") {
                    // --8<-- [start:faker_locale_two]
                    faker.name.nameWithMiddle() // => Sofie Ødegård Løken
                    // --8<-- [end:faker_locale_two]
                }
            }

            context("Unique data generation") {
                it("should generate unique values in Address category") {
                    // --8<-- [start:unique_data_zero]
                    val faker = Faker()
                    // --8<-- [end:unique_data_zero]

                    // --8<-- [start:unique_data_one]
                    faker.unique.configuration {
                        // enable generation of unique values
                        // for address data provider
                        enable(faker::address)
                    }
                    val countries = List(100) { faker.address.country() }
                    assertEquals(countries.distinct().size, 100)
                    // --8<-- [end:unique_data_one]
                }
                it("should generate unique countries, but not unique cities") {
                    val faker = Faker()
                    // --8<-- [start:unique_data_two]
                    val countries = List(100) { faker.address.unique.country() }
                    val cities = List(1000) { faker.address.city() }

                    assertEquals(countries.distinct().size, 100)
                    assertTrue(cities.distinct().size < 1000)
                    // --8<-- [end:unique_data_two]
                }
            }

            context("RandomProvider") {
                // --8<-- [start:and_more_zero]
                val faker = Faker()
                // --8<-- [end:and_more_zero]

                it("should generate random instance of a class") {
                    // --8<-- [start:random_class_instance_one]
                    class Foo(val a: String)
                    class Bar(val foo: Foo)

                    val foo: Foo = faker.randomClass.randomClassInstance()
                    val bar: Bar = faker.randomClass.randomClassInstance()
                    // --8<-- [end:random_class_instance_one]
                }
                it("should generate types by configuration") {
                    // --8<-- [start:random_class_instance_two]
                    fun string() = "X3a8s813dcb"
                    fun uuid() = "00000000-0000-0000-0000-000000000000"
                    class Baz(val id: Int, val uuid: UUID, val username: String)

                    val baz: Baz =
                        faker.randomClass.randomClassInstance {
                            // ヽ(^o^)丿 ᕕ(ᐛ)ᕗ Prepend string type parameter
                            // values with parameter name!
                            typeGenerator<String> { parameterInfo ->
                                "${parameterInfo.name}_${string()}"
                            }
                            typeGenerator<UUID> { UUID.fromString(uuid()) }
                            typeGenerator<Int> { faker.random.nextInt(min = 0, max = 9) }
                        }

                    assertEquals(baz.username, "username_${string()}")
                    assertEquals(baz.uuid, UUID.fromString(uuid()))
                    assertTrue(baz.id in 0..9)
                    // --8<-- [end:random_class_instance_two]
                }
            }

            context("Faker.random") {
                val faker = Faker()
                it("should generate random stuff") {
                    // --8<-- [start:random_service_one]
                    faker.random.nextInt(intRange = 0..1000)
                    faker.random.nextLong(bound = 999L)
                    faker.random.randomString(length = 99)
                    faker.random.nextEnum<TestEnum>()
                    faker.random.nextEnum(TestEnum::class.java) {
                        it != TestEnum.SOME // Exclude 'SOME' enum
                    }
                    faker.random.nextUUID()
                    // --8<-- [end:random_service_one]
                }
            }

            context("StringProvider") {
                val faker = Faker()
                it("should generate random stuff") {
                    // --8<-- [start:string_provider_one]
                    faker.string.numerify("foo###bar") // foo123bar
                    faker.string.letterify("foo???bar", true) // fooXYZbar
                    faker.string.bothify("foo?##bar", false) // foox42bar
                    faker.string.regexify("""\d{2}\w""") // 42a
                    // --8<-- [end:string_provider_one]
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
    SOME,
}
