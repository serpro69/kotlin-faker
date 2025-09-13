package io.github.serpro69.kfaker.integration.provider

import io.github.serpro69.kfaker.Faker
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.ints.negative
import io.kotest.matchers.ints.shouldBeInRange
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldStartWith

class MoneyIT :
    DescribeSpec({
        describe("Money provider class") {
            val money = Faker().money

            context("monetaryAmount function") {

                // TODO: other locales
                it("should contain a currency symbol") {
                    val amount = money.amount(0..99, generateDecimals = false)
                    amount shouldStartWith "$"
                }

                repeat(99) {
                    it(
                        "should return a valid amount within the specified range without decimals run#$it"
                    ) {
                        val amount =
                            money.amount(0..99, generateDecimals = false).replace("$", "").toInt()

                        amount shouldBeInRange 0..99
                    }
                }

                repeat(99) {
                    it(
                        "should return a valid amount within the specified range with decimals run#$it"
                    ) {
                        val amount = money.amount(0..99, generateDecimals = true).replace("$", "")

                        val value = amount.dropLast(3).toInt()
                        val decimal = amount.takeLast(2).toInt()

                        assertSoftly {
                            value shouldBeInRange 0..99
                            decimal shouldBeInRange 0..99
                        }
                    }
                }

                it("should return a negative amount") {
                    val amount =
                        money.amount(-99 until 0, generateDecimals = false).replace("$", "").toInt()

                    amount shouldBe negative()
                }
            }
        }
    })
