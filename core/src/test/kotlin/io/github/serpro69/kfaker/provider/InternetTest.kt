package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.helper.isPrivateNet
import io.github.serpro69.kfaker.helper.isReservedNet
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldMatch
import sun.net.util.IPAddressUtil

class InternetTest: DescribeSpec({
    describe("Internet provider") {
        val internet = Internet(FakerService(faker = Faker()))

        context("IPv4 address generation") {
            repeat(100) {
                it("should generate private IPv4 address run#$it") {
                    isPrivateNet(internet.privateIPv4Address()) shouldBe true
                    isReservedNet(internet.privateIPv4Address()) shouldBe true
                    IPAddressUtil.isIPv4LiteralAddress(internet.privateIPv4Address()) shouldBe true
                }
                it("should generate a public IPv4 address run#$it") {
                    isPrivateNet(internet.publicIPv4Address()) shouldBe false
                    isReservedNet(internet.publicIPv4Address()) shouldBe false
                    IPAddressUtil.isIPv4LiteralAddress(internet.publicIPv4Address()) shouldBe true
                }
                it("should generate a random IPv4 address run#$it") {
                    IPAddressUtil.isIPv4LiteralAddress(internet.iPv4Address()) shouldBe true
                    internet.iPv4Address().split(".").all { s -> s.toInt() in 0..255 } shouldBe true
                }
            }
        }

        context("IPv6 address generation") {
            repeat(100) {
                it("should generate a valid IPv6 address run#$it") {
                    IPAddressUtil.isIPv6LiteralAddress(internet.iPv6Address()) shouldBe true
                }
            }
        }

        @OptIn(ExperimentalStdlibApi::class)
        context("MacAddress generation") {
            repeat(100) {
                it("should generate a valid mac-address run#$it") {
                    internet.macAddress() shouldMatch Regex("^([0-9a-f]{2}:){5}[0-9a-f]{2}$")
                    internet.macAddress().split(":").all { s -> s.hexToInt() in 0..255 }
                }
                it("should generate a valid mac-address with prefix run#$it") {
                    internet.macAddress("a").split(":").all { s -> s.hexToInt() in 0..255 }
                    internet.macAddress("aa").split(":").all { s -> s.hexToInt() in 0..255 }
                    internet.macAddress("aa:ce").split(":").all { s -> s.hexToInt() in 0..255 }
                    internet.macAddress("aa:ce:").split(":").all { s -> s.hexToInt() in 0..255 }
                }
            }
        }
    }
})
