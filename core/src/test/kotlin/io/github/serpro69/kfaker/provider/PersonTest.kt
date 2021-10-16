package io.github.serpro69.kfaker.provider

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.*

class PersonTest : DescribeSpec({
    describe("Person provider") {
        val person = Person(Random(42))

        context("birthDate(age) fun") {
            val now = LocalDate.now()

            repeat(366) {
                it("should generate a random birthDate based on the age run#$it") {
                    ChronoUnit.YEARS.between(person.birthDate(36), now) shouldBe 36
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
                it("should generate a random birthDate with age at certain date run#$it") {
                    ChronoUnit.YEARS.between(person.birthDate(36, start), start) shouldBe 36
                }
            }

            repeat(366) {
                it("should generate a random birthDate with age 0 run#$it") {
                    val at = LocalDate.of(2021, 1, 1)
                    val bd = person.birthDate(0, at)
                    ChronoUnit.YEARS.between(bd, at) shouldBe 0
                }
            }
        }
    }
})
