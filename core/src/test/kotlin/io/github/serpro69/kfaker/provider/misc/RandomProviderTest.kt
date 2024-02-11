package io.github.serpro69.kfaker.provider.misc

import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.faker
import io.github.serpro69.kfaker.fakerConfig
import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.collections.shouldBeSortedWith
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.collections.shouldNotBeSortedWith
import io.kotest.matchers.collections.shouldNotContainAnyOf
import io.kotest.matchers.ints.shouldBeInRange
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.beInstanceOf
import java.util.*

class RandomProviderTest : DescribeSpec({
    describe("RandomProvider instance") {
        val fakerService = FakerService(faker {
            fakerConfig {
                random = Random()
                uniqueGeneratorRetryLimit = 1000000
            }
        })
        val random = RandomProvider(fakerService)

        context("nextInt(bound) fun") {
            val values = List(100) { random.nextInt(10) }

            it("return value should be within specified range") {
                values.all { it < 10 } shouldBe true
            }
        }

        context("nextInt(min, max) fun") {
            val values = List(100) { random.nextInt(6..8) }

            it("return value should be within specified range") {
                values.all { it in 6..8 } shouldBe true
            }
        }

        context("nextInt(intRange) fun") {
            val values = List(100) { random.nextInt(3..9) }

            it("return value should be within specified range") {
                values.all { it in 3..9 } shouldBe true
            }
        }

        context("randomValue<T>(list) fun") {
            context("list is not empty") {
                val values = List(100) { random.nextInt(3..9) }
                val value = random.randomValue(values)

                it("return value should be in the list") {
                    values shouldContain value
                }
            }

            context("list is empty") {
                val values = listOf<String>()

                it("exception is thrown") {
                    shouldThrow<IllegalArgumentException> {
                        random.randomValue(values)
                    }
                }
            }

            context("list contains nulls") {
                val values = listOf(1, 2, 3, null).filter { it == null }
                val value = random.randomValue(values)

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
                val values = Array(100) { random.nextInt(3..9) }
                val value = random.randomValue(values)

                it("return value should be in the array") {
                    values shouldContain value
                }
            }

            context("array is empty") {
                val values = arrayOf<String>()

                it("exception is thrown") {
                    shouldThrow<IllegalArgumentException> {
                        random.randomValue(values)
                    }
                }
            }

            context("array contains nulls") {
                val values = arrayOf(1, 2, 3, null).filter { it == null }
                val value = random.randomValue(values)

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
                    val chars = List(1000) { random.nextLetter(false) }
                    chars.all { source.contains(it, ignoreCase = false) }
                }
            }

            context("upper is set to true") {
                it("random upper-case letter is generated") {
                    val chars = List(1000) { random.nextLetter(true) }
                    chars.all { source.contains(it, ignoreCase = false) }
                }
            }
        }

        context("nextLong(bound) fun") {
            val values = List(100000) { random.nextLong(100) }

            it("return value should be within 0 until 'bound' range") {
                values.all { it in 0 until 100 } shouldBe true
            }
        }

        context("randomString() fun") {
            it("default generated string is 24 char length") {
                random.randomString().length shouldBe 24
            }
        }

        context("nextEnum() fun") {
            it("should return a random enum entry of TestEnum type") {
                val enum = random.nextEnum<TestEnumRandom>()
                enum shouldBeIn TestEnumRandom.values()
            }

            it("should return a random enum entry of Class<TestEnumRandom> type") {
                val enum = random.nextEnum(TestEnumRandom::class.java)
                enum shouldBeIn TestEnumRandom.values()
            }

            it("should return a random enum entry from an array of TestEnum types") {
                val enum = random.nextEnum(TestEnumRandom.values())
                enum shouldBeIn TestEnumRandom.values()
            }

            repeat(10) {
                it("should return a random enum entry based on a predicate #$it") {
                    val enum = random.nextEnum(TestEnumRandom::class.java) { e ->
                        e == TestEnumRandom.THREE || e == TestEnumRandom.SIX
                    }
                    enum shouldBeIn listOf(TestEnumRandom.THREE, TestEnumRandom.SIX)
                }
            }

            repeat(10) {
                it("should not return an enum entry if it's name is excluded #$it") {
                    val enums = List(10) {
                        random.nextEnum<TestEnumRandom>(TestEnumRandom.ONE.name, TestEnumRandom.TWO.name)
                    }
                    enums shouldNotContainAnyOf listOf(TestEnumRandom.ONE, TestEnumRandom.TWO)
                }
            }
        }

        context("nextUUID() fun") {
            val uuid = random.nextUUID()
            it("random UUID is generated") {
                UUID.fromString(uuid) should beInstanceOf(UUID::class)
            }
        }

        context("randomAlphanumeric() fun") {
            it("generates a string from the alphaNumeric source") {
                val sourceGenerated = ('A'..'Z') + ('a'..'z') + ('0'..'9')
                val listAlphanumeric = List(100) { random.randomString(100) }
                listAlphanumeric.forEach {
                    sourceGenerated shouldContainAll it.map { c -> c }
                }
            }

            it("consecutive runs return different values") {
                val one = random.randomString()
                val two = random.randomString()
                one shouldNotBe two
            }

            it("using same seed returns same values") {
                val c1 = FakerService(faker { fakerConfig { this.random = Random(42) } })
                val c2 = FakerService(faker { fakerConfig { this.random = Random(42) } })
                val r1 = RandomProvider(c1)
                val r2 = RandomProvider(c2)
                r1.randomString() shouldBe r2.randomString()
            }

            it("default generated string is 24 char length") {
                val defaultLength = 24
                random.randomString().length shouldBe defaultLength
            }

            it("should return a specific length of the string") {
                val expectedLength = 100
                random.randomString(expectedLength).length shouldBe expectedLength
            }

            it("returns an empty string when length is 0") {
                random.randomString(0) shouldBe ""
            }
        }

        context("randomSublist() fun") {
            val list = List(100) { it }

            repeat(100) {
                it("should return a random sublist run#$it") {
                    val sublist = random.randomSublist(list)
                    assertSoftly {
                        list shouldContainAll sublist
                        if (sublist.isNotEmpty()) sublist shouldBeSortedWith Comparator { o1, o2 -> o1.compareTo(o2) }
                    }
                }

                it("should return a random sublist of a given size run#$it") {
                    val sublist = random.randomSublist(list, size = 10)
                    assertSoftly {
                        list shouldContainAll sublist
                        sublist shouldHaveSize 10
                        if (sublist.isNotEmpty()) sublist shouldBeSortedWith Comparator { o1, o2 -> o1.compareTo(o2) }
                    }
                }

                it("should return a random sublist of a size withing a given range run#$it") {
                    val sublist = random.randomSublist(list, sizeRange = 12..42)
                    assertSoftly {
                        list shouldContainAll sublist
                        sublist.size shouldBeInRange 12..42
                        if (sublist.isNotEmpty()) sublist shouldBeSortedWith Comparator { o1, o2 -> o1.compareTo(o2) }
                    }
                }
            }

            it("should return a random shuffled sublist") {
                val sublist = random.randomSublist(list, size = 10, shuffled = true)
                assertSoftly {
                    list shouldContainAll sublist
                    if (sublist.size > 1) sublist shouldNotBeSortedWith Comparator { o1, o2 -> o1.compareTo(o2) }
                }
            }
        }

        context("randomSubset() fun") {
            val set = setOf(*List(100) { it }.toTypedArray())

            repeat(100) {
                it("should return a random subset run#$it") {
                    val subset = random.randomSubset(set)
                    assertSoftly {
                        set shouldContainAll subset
                        if (subset.isNotEmpty()) subset shouldBeSortedWith Comparator { o1, o2 -> o1.compareTo(o2) }
                    }
                }

                it("should return a random subset of a given size run#$it") {
                    val subset = random.randomSubset(set, size = 10)
                    assertSoftly {
                        set shouldContainAll subset
                        subset shouldHaveSize 10
                        if (subset.isNotEmpty()) subset shouldBeSortedWith Comparator { o1, o2 -> o1.compareTo(o2) }
                    }
                }
                it("should return a random subset of a size witing a given range run#$it") {
                    val subset = random.randomSubset(set, sizeRange = 10..20)
                    assertSoftly {
                        set shouldContainAll subset
                        subset.size shouldBeInRange 10..20
                        if (subset.isNotEmpty()) subset shouldBeSortedWith Comparator { o1, o2 -> o1.compareTo(o2) }
                    }
                }
            }

            it("should return a random shuffled subset") {
                val subset = random.randomSubset(set, shuffled = true)
                assertSoftly {
                    set shouldContainAll subset
                    if (subset.size > 1) subset shouldNotBeSortedWith Comparator { o1, o2 -> o1.compareTo(o2) }
                }
            }
        }

        context("local unique provider") {
            repeat(10) {
                it("should generate unique primitive values run#$it") {
                    val ints = List(21) {
                        random.unique.nextInt(42)
                    }
                    ints.distinct() shouldHaveSize 21
                    // cleanup
                    random.unique.clear(RandomProvider.Key.NEXT_INT)
                }

                it("should generate unique values from a list run#$it") {
                    // arrange
                    val ints = List(21) {
                        random.unique.nextInt(42)
                    }
                    // act
                    val randomInts = List(10) {
                        random.unique.randomValue(ints)
                    }
                    // assert
                    ints shouldContainAll randomInts
                    randomInts.distinct() shouldHaveSize 10
                    // cleanup
                    random.unique.clear(RandomProvider.Key.NEXT_INT)
                    random.unique.clear(RandomProvider.Key.RANDOM_VALUE)
                }

                it("should return unique enums run#$it") {
                    val enums = List(9) {
                        random.unique.nextEnum<TestEnumRandom>()
                    }
                    enums.distinct().size shouldBe 9
                    random.clear(RandomProvider.Key.NEXT_INT)
                    random.clear(RandomProvider.Key.NEXT_ENUM)
                }

                it("shouldn return unique enums with predicate run#$it") {
                    val excluded = listOf(TestEnumRandom.ONE, TestEnumRandom.TWO, TestEnumRandom.THREE)
                    val enums = List(6) {
                        random.unique.nextEnum(TestEnumRandom::class.java) { e -> e !in excluded }
                    }
                    enums.distinct().size shouldBe 6
                    enums shouldNotContainAnyOf excluded
                    random.clear(RandomProvider.Key.NEXT_INT)
                    random.clear(RandomProvider.Key.NEXT_ENUM)
                }


                it("shouldn return unique enums excluding some names run#$it") {
                    val excluded = listOf(TestEnumRandom.ONE, TestEnumRandom.TWO, TestEnumRandom.THREE)
                    val enums = List(6) {
                        random.unique.nextEnum<TestEnumRandom>(*excluded.map { e -> e.name }.toTypedArray())
                    }
                    enums.distinct().size shouldBe 6
                    enums shouldNotContainAnyOf excluded
                    random.clear(RandomProvider.Key.NEXT_INT)
                    random.clear(RandomProvider.Key.NEXT_ENUM)
                }

                it("should return unique sublits run#$it") {
                    // arrange
                    val ints = List(21) {
                        random.unique.nextInt(42)
                    }
                    // act
                    val randomInts = List(10) {
                        random.unique.randomSublist(ints, 6)
                    }
                    // assert
                    ints shouldContainAll randomInts.flatten()
                    randomInts.distinct() shouldHaveSize 10
                    // cleanup
                    random.unique.clear(RandomProvider.Key.NEXT_INT)
                    random.unique.clear(RandomProvider.Key.RANDOM_SUBLIST)
                }
            }
        }

        context("global unique provider") {
            val config = fakerConfig { uniqueGeneratorRetryLimit = 100 }
            val faker = Faker(config)
            faker.unique.configuration {
                enable(faker::random)
            }
            repeat(10) {
                it("should generate unique primitive values run#$it") {
                    val ints = List(21) {
                        random.nextInt(42)
                    }
                    ints.distinct() shouldHaveSize 21
                    // cleanup
                    faker.unique.clear(faker::random)
                }

                it("should generate unique values from a list run#$it") {
                    // arrange
                    val ints = List(21) {
                        random.nextInt(42)
                    }
                    // act
                    val randomInts = List(10) {
                        random.randomValue(ints)
                    }
                    // assert
                    ints shouldContainAll randomInts
                    randomInts.distinct() shouldHaveSize 10
                    // cleanup
                    faker.unique.clear(faker::random)
                }

                it("should return unique enums run#$it") {
                    val enums = List(9) {
                        random.nextEnum<TestEnumRandom>()
                    }
                    enums.distinct().size shouldBe 9
                    faker.unique.clear(faker::random)
                }

                it("shouldn return unique enums with predicate run#$it") {
                    val excluded = listOf(TestEnumRandom.ONE, TestEnumRandom.TWO, TestEnumRandom.THREE)
                    val enums = List(6) {
                        random.nextEnum(TestEnumRandom::class.java) { e -> e !in excluded }
                    }
                    enums.distinct().size shouldBe 6
                    enums shouldNotContainAnyOf excluded
                    faker.unique.clear(faker::random)
                }


                it("shouldn return unique enums excluding some names run#$it") {
                    val excluded = listOf(TestEnumRandom.ONE, TestEnumRandom.TWO, TestEnumRandom.THREE)
                    val enums = List(6) {
                        random.nextEnum<TestEnumRandom>(*excluded.map { e -> e.name }.toTypedArray())
                    }
                    enums.distinct().size shouldBe 6
                    enums shouldNotContainAnyOf excluded
                    faker.unique.clear(faker::random)
                }

                it("should return unique sublits run#$it") {
                    // arrange
                    val ints = List(21) {
                        random.nextInt(42)
                    }
                    // act
                    val randomInts = List(10) {
                        random.randomSublist(ints, 6)
                    }
                    // assert
                    ints shouldContainAll randomInts.flatten()
                    randomInts.distinct() shouldHaveSize 10
                    // cleanup
                    faker.unique.clear(faker::random)
                }
            }
        }
    }
})

enum class TestEnumRandom {
    ZERO,
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE,
    TEN,
    ELEVEN,
    TWELVE,
    ;
}
