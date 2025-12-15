package io.github.serpro69.kfaker.provider

import com.google.common.net.InetAddresses
import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.helper.isPrivateNet
import io.github.serpro69.kfaker.helper.isReservedNet
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldMatch

class InternetTest :
    DescribeSpec({
        describe("Internet provider") {
            val s = FakerService(Faker())
            val internet = Internet(s, Name(s))

            context("IPv4 address generation") {
                repeat(100) {
                    it("should generate private IPv4 address run#$it") {
                        isPrivateNet(internet.privateIPv4Address()) shouldBe true
                        isReservedNet(internet.privateIPv4Address()) shouldBe true
                        InetAddresses.isInetAddress(internet.privateIPv4Address()) shouldBe true
                    }
                    it("should generate a public IPv4 address run#$it") {
                        isPrivateNet(internet.publicIPv4Address()) shouldBe false
                        isReservedNet(internet.publicIPv4Address()) shouldBe false
                        InetAddresses.isInetAddress(internet.publicIPv4Address()) shouldBe true
                    }
                    it("should generate a random IPv4 address run#$it") {
                        InetAddresses.isInetAddress(internet.iPv4Address()) shouldBe true
                        internet.iPv4Address().split(".").all { s -> s.toInt() in 0..255 } shouldBe
                            true
                    }
                }
            }

            context("IPv6 address generation") {
                repeat(100) {
                    it("should generate a valid IPv6 address run#$it") {
                        InetAddresses.isInetAddress(internet.iPv6Address()) shouldBe true
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
