package io.github.serpro69.kfaker

import io.kotlintest.*
import io.kotlintest.specs.*
import java.util.*

class FakerConfigTest : DescribeSpec() {

    override fun isolationMode() = IsolationMode.InstancePerLeaf

    init {
        describe("random is set through FakerConfig") {
            val fakerConfig = FakerConfig.builder().create {
                random = Random(42)
            }

            val faker = Faker(fakerConfig)

            val city1 = faker.address.city()
            val name1 = faker.name.name()

            context("random is seeded with the same value") {
                val otherFakerConfig = FakerConfig.builder().create {
                    random = Random(42)
                }
                val otherFaker = Faker(otherFakerConfig)

                it("the output of repeated function calls should be the same") {
                    val city2 = otherFaker.address.city()
                    val name2 = otherFaker.name.name()

                    assertSoftly {
                        city2 shouldBe city1
                        name2 shouldBe name1
                    }
                }
            }

/*            context("Faker is re-initialized without setting Faker.Config.random") {
                Faker.init()

                val rand2 = Faker.Config.random

                it("the output of repeated function calls should be different") {
                    val city2 = Faker.address.city()
                    val name2 = Faker.name.name()

                    assertSoftly {
                        city2 shouldNotBe city1
                        name2 shouldNotBe name1

                        rand2 shouldNotBe rand1
                    }
                }
            }*/
        }
    }
}