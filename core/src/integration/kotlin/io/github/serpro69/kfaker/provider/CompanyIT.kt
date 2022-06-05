package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.faker
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldMatch

class CompanyIT : DescribeSpec({
    describe("Company Provider") {
        val company: (locale: String) -> Company = { faker { fakerConfig { locale = it } }.company }

        context("ja locale") {
            it("should generate a valid name") {
                shouldNotThrow<NoSuchElementException> { company("ja").name() shouldNotBe "" }
            }
        }
    }
})
