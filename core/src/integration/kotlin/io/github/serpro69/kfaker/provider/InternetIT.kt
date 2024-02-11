package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.faker
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldEndWith
import io.kotest.matchers.string.shouldMatch
import io.kotest.matchers.string.shouldNotContain
import io.kotest.matchers.string.shouldStartWith

@Suppress("unused")
class InternetIT : DescribeSpec({
    describe("Internet provider") {
        val faker = Faker()
        val ukFaker = faker { fakerConfig { locale = "uk" } }
        val internet = faker.internet

        // https://stackoverflow.com/a/201378/5917497
        val emailRegex = Regex("""
            (?:[a-z0-9!#${'$'}%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#${'$'}%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])
        """.trimIndent())

        context("generates a domain name") {
            internet.domain() shouldMatch Regex("""[a-z]+(-[a-z]+)?\.(example|test)""")
            internet.domain(subdomain = true).split(".") shouldHaveSize 3
            internet.domain(subdomain = true) shouldMatch Regex("""[a-z]+(-[a-z]+)?\.[a-z]+(-[a-z]+)?\.(example|test)""")
            internet.domain(domain = "foo") shouldStartWith "foo"
            internet.domain(subdomain = true, domain = "bar").split(".")[1] shouldBe "bar"
            internet.domain(domain = "kotlin-faker.test") shouldBe "kotlin-faker.test"
            internet.domain(subdomain = true, domain = "kotlin-faker.test").substringAfter(".") shouldBe "kotlin-faker.test"
            internet.domain(subdomain = true, domain = "faker.kotlin-faker.test") shouldBe "faker.kotlin-faker.test"
            // check domain normalization from Ukrainian lang
            ukFaker.internet.domain() shouldMatch Regex("""[a-z]+(-[a-z]+)?\.(example|test)""")
            ukFaker.internet.domain(subdomain = true) shouldMatch Regex("""[a-z]+(-[a-z]+)?\.[a-z]+(-[a-z]+)?\.(example|test)""")
        }

        context("generates an email address") {
            val emails = List(1000) { internet.email() }

            it("should be valid") {
                assertSoftly { emails.forEach { it shouldMatch emailRegex } }
            }
        }

        context("generates a safe email address") {
            val emails = List(1000) { internet.safeEmail() }

            it("should be valid") {
                assertSoftly { emails.forEach { it shouldMatch emailRegex } }
            }

            it("should end with .test domain") {
                emails.all { it.endsWith(".test") } shouldBe true
            }
        }
    }
})
