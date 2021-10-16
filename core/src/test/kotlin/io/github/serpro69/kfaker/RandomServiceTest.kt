package io.github.serpro69.kfaker

import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.beInstanceOf
import java.util.*

internal class RandomServiceTest : DescribeSpec({
    describe("RandomService instance") {
        val randomService = RandomService(Random())

        context("nextInt(bound) fun") {
            val values = List(100) { randomService.nextInt(10) }

            it("return value should be within specified range") {
                values.all { it < 10 } shouldBe true
            }
        }

        context("nextInt(min, max) fun") {
            val values = List(100) { randomService.nextInt(6..8) }

            it("return value should be within specified range") {
                values.all { it in 6..8 } shouldBe true
            }
        }

        context("nextInt(intRange) fun") {
            val values = List(100) { randomService.nextInt(3..9) }

            it("return value should be within specified range") {
                values.all { it in 3..9 } shouldBe true
            }
        }

        context("randomValue<T>(list) fun") {
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

        context("randomValue<T>(array) fun") {
            context("array is not empty") {
                val values = Array(100) { randomService.nextInt(3..9) }
                val value = randomService.randomValue(values)

                it("return value should be in the array") {
                    values shouldContain value
                }
            }

            context("array is empty") {
                val values = arrayOf<String>()

                it("exception is thrown") {
                    shouldThrow<IllegalArgumentException> {
                        randomService.randomValue(values)
                    }
                }
            }

            context("array contains nulls") {
                val values = arrayOf(1, 2, 3, null).filter { it == null }
                val value = randomService.randomValue(values)

                it("return value should be in the array") {
                    assertSoftly {
                        values shouldContain value
                        value shouldBe null
                    }
                }
            }
        }

        context("nextLetter() fun") {
            val source = "qwertyuiopasdfghjklzxcvbnm"

            context("upper is set to false") {
                it("random lower-case letter is generated") {
                    val chars = List(1000) { randomService.nextLetter(false) }
                    chars.all { source.contains(it, ignoreCase = false) }
                }
            }

            context("upper is set to true") {
                it("random upper-case letter is generated") {
                    val chars = List(1000) { randomService.nextLetter(true) }
                    chars.all { source.contains(it, ignoreCase = false) }
                }
            }
        }

        context("nextLong(bound) fun") {
            val values = List(100) { randomService.nextLong(10) }

            it("return value should be within specified range") {
                values.all { it < 10 } shouldBe true
            }
        }

        context("nextString() fun") {
            it("default generated string is 100 char length") {
                randomService.nextString().length shouldBe 100
            }
        }

        context("nextEnum() fun") {
            it("should return a random enum entry of TestEnum type") {
                val enum = randomService.nextEnum<TestEnum>()
                enum shouldBeIn TestEnum.values()
            }

            it("should return a random enum entry of Class<TestEnum> type") {
                val enum = randomService.nextEnum(TestEnum::class.java)
                enum shouldBeIn TestEnum.values()
            }

            it("should return a random enum entry from an array of TestEnum types") {
                val enum = randomService.nextEnum(TestEnum.values())
                enum shouldBeIn TestEnum.values()
            }

            repeat(10) {
                it("should return a random enum entry based on a predicate #$it") {
                    val enum = randomService.nextEnum(TestEnum::class.java) { e ->
                        e == TestEnum.THREE || e == TestEnum.SIX
                    }
                    enum shouldBeIn listOf(TestEnum.THREE, TestEnum.SIX)
                }
            }

            repeat(10) {
                it("should not return an enum entry if it's name is excluded #$it") {
                    val enum = randomService.nextEnum<TestEnum>(TestEnum.ONE.name)
                    enum shouldBeIn listOf(
                        TestEnum.TWO,
                        TestEnum.THREE,
                        TestEnum.FOUR,
                        TestEnum.FIVE,
                        TestEnum.SIX
                    )
                }
            }
        }

        context("nextUUID() fun") {
            val uuid = randomService.nextUUID()
            it("random UUID is generated") {
                UUID.fromString(uuid) should beInstanceOf(UUID::class)
            }
        }

        context("randomAlphanumeric() fun") {
            it("fun is generated") {
                val sourceGenerated = ('A'..'Z') + ('a'..'z') + ('0'..'9')
                val listAlphanumeric = List(100) { randomService.randomAlphanumeric() }
                listAlphanumeric.forEach { it -> it.all { it in sourceGenerated } }
            }

            it("returns different values") {
                val one = randomService.randomAlphanumeric()
                val two = randomService.randomAlphanumeric()
                one shouldNotBe two
            }

            it("default generated string is 10 char length") {
                val defaultLength = 10
                randomService.randomAlphanumeric().length shouldBe defaultLength
            }

            it("should return a specific length of the string") {
                val expectedLength = 100
                randomService.randomAlphanumeric(expectedLength).length shouldBe expectedLength
            }

            it("employ string else null length") {
                val expectedLength = 0
                randomService.randomAlphanumeric(expectedLength).length shouldBe expectedLength
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
    SIX
}
