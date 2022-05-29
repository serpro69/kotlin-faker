package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.faker
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class SeparatorIT : DescribeSpec({
    describe("Separator Provider") {
        val sep: (locale: String) -> Separator = { faker { fakerConfig { locale = it } }.separator }

        context("en locale") {
            it("should return separator symbol for 'en' locale") {
                sep("en").separator() shouldBe " & "
            }
        }

        context("uk locale") {
            it("should return localized separator symbol") {
                sep("uk").separator() shouldBe " та "
            }
        }

        context("nb-NO locale") {
            it("should default to 'en' separator symbol if not localized") {
                sep("nb-NO").separator() shouldBe " & "
            }
        }
    }
})
