package io.github.serpro69.kfaker.provider

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.comparables.shouldBeGreaterThanOrEqualTo
import io.kotest.matchers.comparables.shouldBeLessThanOrEqualTo
import io.kotest.matchers.shouldBe
import java.time.LocalDate
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
    }
})

private const val age = 36L

private fun assertAge(birthDate: LocalDate, start: LocalDate) {
    assertSoftly {
        birthDate shouldBeGreaterThanOrEqualTo start.minusYears(age)
        birthDate shouldBeLessThanOrEqualTo start.minusYears(age - 1).minusDays(1)
    }
}
