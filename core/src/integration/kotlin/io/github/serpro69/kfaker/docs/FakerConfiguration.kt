package io.github.serpro69.kfaker.docs

import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.faker
import io.github.serpro69.kfaker.fakerConfig
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import org.junit.jupiter.api.Assertions.assertEquals
import java.util.UUID
import kotlin.random.Random

@DisplayName("Snippets used in Orchid docs 'Faker Configuration' wiki page")
class FakerConfiguration : DescribeSpec({
    describe("FakerConfig") {
        context("Deterministic Random") {
            it("two faker instances with same 'random' should output same values") {
                // --8<-- [start:faker_config_one]
                val config = fakerConfig { random = Random(42) }
                val faker = Faker(config)
                val city1 = faker.address.city()
                val name1 = faker.name.name()

                val otherConfig = fakerConfig { random = Random(42) }
                val otherFaker = Faker(otherConfig)
                val city2 = otherFaker.address.city()
                val name2 = otherFaker.name.name()

                assertEquals(city1, city2)
                assertEquals(name1, name2)
                // --8<-- [end:faker_config_one]
            }

            it("two faker instances with same 'randomSeed' should output same values") {
                // --8<-- [start:faker_config_two]
                val config = fakerConfig { randomSeed = 42 }
                val faker = Faker(config)
                // --8<-- [end:faker_config_two]
                val city1 = faker.address.city()
                val name1 = faker.name.name()

                val otherConfig = fakerConfig { randomSeed = 42 }
                val otherFaker = Faker(otherConfig)
                val city2 = otherFaker.address.city()
                val name2 = otherFaker.name.name()

                assertEquals(city1, city2)
                assertEquals(name1, name2)
            }

            it("'random' should be ignored if 'randomSeed' is specified") {
                // --8<-- [start:faker_config_three]
                val config = fakerConfig {
                    random = Random(123)
                    randomSeed = 42
                }
                val faker = Faker(config)
                val city1 = faker.address.city()

                val otherConfig = fakerConfig {
                    random = Random(123)
                    randomSeed = 42
                }
                val otherFaker = Faker(otherConfig)
                val city2 = otherFaker.address.city()

                assertEquals(city1, city2)
                // --8<-- [end:faker_config_three]
            }
        }

        context("Locale") {
            it("should use a default 'en' locale") {
                // --8<-- [start:faker_config_four]
                val faker = Faker()
                assertEquals(faker.address.defaultCountry(), "United States of America")
                // --8<-- [end:faker_config_four]
            }

            it("should be able to configure locale") {
                // --8<-- [start:faker_config_five]
                val config = fakerConfig { locale = "nb-NO" }
                val faker = Faker(config)
                assertEquals(faker.address.defaultCountry(), "Norge")
                // --8<-- [end:faker_config_five]
            }
        }

        context("RandomClassProvider") {
            it("should configure RandomClassProvider") {
                // --8<-- [start:faker_config_six]
                class Test(
                    val uuid: UUID,
                    val name: String,
                )
                val config = fakerConfig {
                    randomClassInstance {
                        collectionsSize = 6
                        typeGenerator<UUID> { UUID.fromString("00000000-0000-0000-0000-000000000000") }
                        namedParameterGenerator("name") { faker {}.name.name() }
                    }
                }
                val test = Faker(config).randomClass.randomClassInstance<Test>()
                assertEquals(test.uuid, UUID.fromString("00000000-0000-0000-0000-000000000000"))
                // --8<-- [end:faker_config_six]
            }
        }
    }
})
