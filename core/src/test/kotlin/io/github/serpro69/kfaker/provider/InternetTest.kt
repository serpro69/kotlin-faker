package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.helper.isPrivateNet
import io.github.serpro69.kfaker.helper.isReservedNet
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class InternetTest: DescribeSpec({
    describe("Internet provider") {
        val internet = Internet(FakerService(faker = Faker()))

        context("IPv4 address generation") {
            repeat(100) {
                it("should generate private IPv4 address run#$it") {
                    isPrivateNet(internet.privateIPv4Address()) shouldBe true
                    isReservedNet(internet.privateIPv4Address()) shouldBe true
                }
                it("should generate a public IPv4 address run#$it") {
                    isPrivateNet(internet.publicIPv4Address()) shouldBe false
                    isReservedNet(internet.publicIPv4Address()) shouldBe false
                }
                it("should generate a random IPv4 address run#$it") {
                    internet.iPv4Address().split(".").all { s -> s.toInt() in 0..255 } shouldBe true
                }
            }
        }
    }
})
