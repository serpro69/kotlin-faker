package io.github.serpro69.kfaker.provider

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.comparables.shouldBeGreaterThanOrEqualTo
import io.kotest.matchers.comparables.shouldBeLessThanOrEqualTo
import io.kotest.matchers.shouldBe
import java.time.LocalDate
import java.time.Month
import java.time.Month.DECEMBER
import java.util.*

class PersonTest : DescribeSpec({
    describe("Person provider") {
        val person = Person(Random(42))

        context("birthDate(age) fun") {
            repeat(366) {
                it("should generate a random birth-date based on the age run#$it") {
                    assertAge(person.birthDate(age), LocalDate.now())
                }
            }

            it("should generate same birth-dates with the same random seed") {
                val p1 = Person(Random(42))
                val p2 = Person(Random(42))
                p1.birthDate(30) shouldBe p2.birthDate(30)
            }
        }

        context("birthDate(age, at) fun") {
            val start = LocalDate.now().minusYears(25)

            repeat(366) {
                it("should generate a random birth-date with explicit startDate run#$it") {
                    assertAge(person.birthDate(age, start), start)
                }
            }
        }

        context("birthDate(age, month) fun") {
            repeat(12) {
                it("should generate a random birth-date for the given month run#$it") {
                    val year = LocalDate.now().minusYears(age).year
                    val month = DECEMBER
                    val bd = person.birthDate(age, month)
                    bd shouldBeGreaterThanOrEqualTo LocalDate.of(year, month, 1)
                    bd shouldBeLessThanOrEqualTo LocalDate.of(year, month, 31)
                }
            }

            repeat(91) {
                it("should generate a random birth-date for one of the given months run#$it") {
                    val year = LocalDate.now().minusYears(age).year
                    val months = listOf(Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER)
                    val bd = person.birthDate(age, months)
                    bd shouldBeGreaterThanOrEqualTo LocalDate.of(year, months.first(), 1)
                    bd shouldBeLessThanOrEqualTo LocalDate.of(year, months.last(), 30)
                }
            }

            it("should take into account leap years for month of February") {
                val leap = List(100) {
                    person.birthDate(25, Month.FEBRUARY, at = LocalDate.now().withYear(2025))
                }

                leap shouldContain LocalDate.parse("2000-02-29")
            }
        }
    }
})

private const val age = 36L

private fun assertAge(birthDate: LocalDate, start: LocalDate) {
    assertSoftly {
        birthDate shouldBeGreaterThanOrEqualTo start.minusYears(age)
        birthDate shouldBeLessThanOrEqualTo start.minusYears(age - 1).minusDays(1)
    }
}
