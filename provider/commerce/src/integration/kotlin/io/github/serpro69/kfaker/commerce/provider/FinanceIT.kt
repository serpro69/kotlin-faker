package io.github.serpro69.kfaker.commerce.provider

import io.github.serpro69.kfaker.commerce.faker
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.string.shouldNotContain

class FinanceIT : DescribeSpec({
    describe("Finance Provider") {
        val finance = faker { }.finance

        context("generexifying strings") {
            repeat(10) {
                it("creditCard() does NOT contain regex expressions run#$it") {
                    assertSoftly {
                        finance.creditCard("maestro") shouldNotContain "["
                        finance.creditCard("maestro") shouldNotContain "]"
                        finance.creditCard("maestro") shouldNotContain "{"
                        finance.creditCard("maestro") shouldNotContain "}"
                    }
                }
            }
        }
    }
})
