package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.Faker
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldMatch

@Suppress("unused")
class InternetTest : DescribeSpec({
    describe("Internet provider") {
        val faker = Faker()
        val internet = faker.internet

        // https://stackoverflow.com/a/201378/5917497
        val emailRegex = Regex("""
            (?:[a-z0-9!#${'$'}%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#${'$'}%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])
        """.trimIndent())

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
