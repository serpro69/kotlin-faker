package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.faker
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class CurrencySymbolIT : DescribeSpec({
    describe("CurrencySymbol Provider") {
        val cs: (locale: String) -> CurrencySymbol = { faker { fakerConfig { locale = it } }.currencySymbol }

        context("en locale") {
            it("should return currency symbol for 'en' locale") {
                cs("en").symbol() shouldBe "$"
            }
        }

        context("nb-NO locale") {
            it("should default to 'en' currency symbol if not localized") {
                cs("nb-NO").symbol() shouldBe "$"
            }
        }
    }
})
