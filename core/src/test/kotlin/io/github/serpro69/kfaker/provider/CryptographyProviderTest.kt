package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.Faker
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.string.shouldMatch

class CryptographyProviderTest : DescribeSpec({
    describe("Cryptography provider") {
        val crypto = Faker().crypto

        it("should generate a valid md5 value") {
            crypto.md5() isValidWithLen 32
        }
        it("should generate a valid sha-1 value") {
            crypto.sha1() isValidWithLen 40
        }
        it("should generate a valid sha-224 value") {
            crypto.sha224() isValidWithLen 56
        }
        it("should generate a valid sha-256 value") {
            crypto.sha256() isValidWithLen 64
        }
        it("should generate a valid sha-384 value") {
            crypto.sha384() isValidWithLen 96
        }
        it("should generate a valid sha-512 value") {
            crypto.sha512() isValidWithLen 128
        }
    }
})

private infix fun String.isValidWithLen(len: Int) {
    this shouldMatch Regex("""^[a-f0-9]{$len}$""")
}
