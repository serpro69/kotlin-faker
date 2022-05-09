package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.faker
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.string.shouldMatch

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

        context("nl locale") {
            it("should generate a valid postcode") {
                address("nl").postcode() shouldMatch Regex("""[1-9]\d{3} \w{2}""")
            }
        }
    }
})
