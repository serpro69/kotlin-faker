package io.github.serpro69.kfaker

import io.kotlintest.*
import io.kotlintest.matchers.collections.*
import io.kotlintest.matchers.string.*
import io.kotlintest.specs.*
import java.util.*

internal class RandomServiceTest : DescribeSpec({
    describe("RandomService instance") {
        val randomService = RandomService(Random())

        context("calling nextInt(min, max)") {
            val values = List(100) { randomService.nextInt(6..8) }

            it("return value should be within specified range") {
                values.all { it in 6..8 } shouldBe true
            }
        }

        context("calling nextInt(intRange)") {
            val values = List(100) { randomService.nextInt(3..9) }

            it("return value should be within specified range") {
                values.all { it in 3..9 } shouldBe true
            }
        }

        context("calling randomValue<T>(list)") {
            context("list is not empty") {
                val values = List(100) { randomService.nextInt(3..9) }
                val value = randomService.randomValue(values)

                it("return value should be in the list") {
                    values shouldContain value
                }
            }

            context("list is empty") {
                val values = listOf<String>()

                it("exception is thrown") {
                    shouldThrow<IllegalArgumentException> {
                        randomService.randomValue(values)
                    }
                }
            }

            context("list contains nulls") {
                val values = listOf(1, 2, 3, null).filter { it == null }
                val value = randomService.randomValue(values)

                it("return value should be in the list") {
                    assertSoftly {
                        values shouldContain value
                        value shouldBe null
                    }
                }
            }
        }

        context("calling nextChar()") {
            val source = "qwertyuiopasdfghjklzxcvbnm"

            context("upperCase is true") {
                it("random upper-case letter is generated") {
                    val letter = randomService.nextLetter(true).toString()
                    source.toUpperCase() shouldContain letter
                }
            }

            context("upperCase is false") {
                it("random lower-case letter is generated") {
                    val letter = randomService.nextLetter(false).toString()
                    source shouldContain letter
                }
            }
        }
    }
})
