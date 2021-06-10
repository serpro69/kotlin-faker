package io.github.serpro69.kfaker.docs

import io.github.serpro69.kfaker.faker
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import org.junit.jupiter.api.Assertions.assertEquals
import java.util.*

@DisplayName("Snippets used in Orchid docs 'Faker Configuration' wiki page")
class FakerConfiguration : DescribeSpec({
    describe("FakerConfig") {
        context("Deterministic Random") {
            it("two faker instances with same 'random' should output same values") {
                // START faker_config_one
                val faker = faker {
                    fakerConfig {
                        random = Random(42)
                    }
                }
                val city1 = faker.address.city()
                val name1 = faker.name.name()

                val otherFaker = faker {
                    fakerConfig {
                        random = Random(42)
                    }
                }
                val city2 = otherFaker.address.city()
                val name2 = otherFaker.name.name()

                assertEquals(city1, city2)
                assertEquals(name1, name2)
                // END faker_config_one
            }

            it("two faker instances with same 'randomSeed' should output same values") {
                // START faker_config_two
                val faker = faker {
                    fakerConfig {
                        randomSeed = 42
                    }
                }
                // END faker_config_two
                val city1 = faker.address.city()
                val name1 = faker.name.name()

                val otherFaker = faker {
                    fakerConfig {
                        randomSeed = 42
                    }
                }
                val city2 = otherFaker.address.city()
                val name2 = otherFaker.name.name()

                assertEquals(city1, city2)
                assertEquals(name1, name2)
            }

            it("'random' should be ignored if 'randomSeed' is specified") {
                // START faker_config_three
                val faker = faker {
                    fakerConfig {
                        random = Random(123)
                        randomSeed = 42
                    }
                }
                val city1 = faker.address.city()

                val otherFaker = faker {
                    fakerConfig {
                        random = Random(42)
                    }
                }
                val city2 = otherFaker.address.city()

                assertEquals(city1, city2)
                // END faker_config_three
            }
        }
    }
})
