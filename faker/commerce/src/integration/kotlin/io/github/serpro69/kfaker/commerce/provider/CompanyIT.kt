package io.github.serpro69.kfaker.commerce.provider

import io.github.serpro69.kfaker.commerce.faker
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldNotBe

class CompanyIT : DescribeSpec({
    describe("Company Provider") {
        val company: (locale: String) -> Company = { faker { fakerConfig { locale = it } }.company }

        context("uk locale") {
            it("should generate a valid name") {
                shouldNotThrow<NoSuchElementException> { company("uk").name() shouldNotBe "" }
            }
        }

        context("ja locale") {
            it("should generate a valid name") {
                shouldNotThrow<NoSuchElementException> { company("ja").name() shouldNotBe "" }
            }
        }
    }
})
