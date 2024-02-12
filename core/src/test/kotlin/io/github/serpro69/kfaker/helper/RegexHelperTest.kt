package io.github.serpro69.kfaker.helper

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class RegexHelperTest : DescribeSpec({

    describe("Regex helpers for Internet") {
        context("private net checker") {
            it("should return true for a private network") {
                isPrivateNet("127.120.80.42") shouldBe true
            }
            it("should return false for a non-private network") {
                isPrivateNet("148.120.80.42") shouldBe false
                isPrivateNet("192.88.99.255") shouldBe false
                isPrivateNet("192.88.199.255") shouldBe false
            }
        }
        context("reserved net checker") {
            it("should return true for a private or reserved network") {
                isReservedNet("127.120.80.42") shouldBe true
                isReservedNet("192.88.99.255") shouldBe true
            }
            it("should return false for a non-private and non-reserved network") {
                isReservedNet("148.120.80.42") shouldBe false
                isReservedNet("192.88.199.255") shouldBe false
            }
        }
    }
})
