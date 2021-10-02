package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.faker
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.core.spec.style.DescribeSpec

class AddressIT : DescribeSpec({
    describe("Address Provider") {
        val address: (locale: String) -> Address = { faker { fakerConfig { locale = it } }.address }

        context("nb-NO locale") {
            it("city() does NOT throw NoSuchElementException") {
                shouldNotThrow<NoSuchElementException> { address("nb-NO").city() }
            }
        }

        context("vi locale") {
            it("city() does NOT throw NoSuchElementException") {
                shouldNotThrow<NoSuchElementException> { address("vi").city() }
            }
        }

        context("lv locale") {
            it("city() does NOT throw NoSuchElementException") {
                shouldNotThrow<NoSuchElementException> { address("lv").city() }
            }
        }
    }
})
