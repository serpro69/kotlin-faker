package io.github.serpro69.kfaker

import io.kotlintest.*
import io.kotlintest.specs.*
import java.util.*

class FakerConfigTest : FreeSpec({
    "GIVEN random is set through FakerConfig" - {
        Faker.Config.random = Random(42)
        Faker.init()

        val city1 = Faker.address.city()
        val name1 = Faker.name.name()

        "WHEN random is seeded with the same value" - {
            Faker.Config.random = Random(42)
            Faker.init()

            "THEN the output of repeated function calls should be the same" {
                val city2 = Faker.address.city()
                val name2 = Faker.name.name()

                assertSoftly {
                    city2 shouldBe city1
                    name2 shouldBe name1
                }
            }
        }

        "WHEN Faker is re-initialized without setting Faker.Config.random" - {
            Faker.init()

            "THEN the output of repeated function calls should be different" {
                val city2 = Faker.address.city()
                val name2 = Faker.name.name()

                assertSoftly {
                    city2 shouldNotBe city1
                    name2 shouldNotBe name1
                }
            }
        }
    }
})