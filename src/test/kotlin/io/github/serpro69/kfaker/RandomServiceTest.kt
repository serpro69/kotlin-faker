package io.github.serpro69.kfaker

import io.kotlintest.*
import io.kotlintest.matchers.collections.*
import io.kotlintest.matchers.string.*
import io.kotlintest.specs.*

internal class RandomServiceTest : FreeSpec({
    "GIVEN RandomService instance" - {
        val randomService = RandomService()

        "WHEN calling nextInt(min, max)" - {
            val values = List(100) { randomService.nextInt(6..8) }

            "THEN return value should be within specified range" {
                values.all { it in 6..8 } shouldBe true
            }
        }

        "WHEN calling nextInt(intRange)" - {
            val values = List(100) { randomService.nextInt(3..9) }

            "THEN return value should be within specified range" {
                values.all { it in 3..9 } shouldBe true
            }
        }

        "WHEN calling randomValue<T>(list)" - {
            "AND list is not empty" - {
                val values = List(100) { randomService.nextInt(3..9) }
                val value = randomService.randomValue(values)

                "THEN return value should be in the list" {
                    values shouldContain value
                }
            }

            "AND list is empty" - {
                val values = listOf<String>()

                "THEN exception is thrown" {
                    shouldThrow<IllegalArgumentException> {
                        randomService.randomValue(values)
                    }
                }
            }

            "AND list contains nulls" - {
                val values = listOf(1, 2, 3, null).filter { it == null }
                val value = randomService.randomValue(values)

                "THEN return value should be in the list" {
                    assertSoftly {
                        values shouldContain value
                        value shouldBe null
                    }
                }
            }
        }

        "WHEN calling nextChar()" - {
            val source = "qwertyuiopasdfghjklzxcvbnm"

            "AND upperCase is true" - {
                "THEN random upper-case letter is generated" {
                    val letter = randomService.nextLetter(true).toString()
                    source.toUpperCase() shouldContain letter
                }
            }

            "AND upperCase is false" - {
                "THEN random lower-case letter is generated" {
                    val letter = randomService.nextLetter(false).toString()
                    source shouldContain letter
                }
            }
        }
    }
}) {
    override fun isolationMode() = IsolationMode.InstancePerLeaf
}
