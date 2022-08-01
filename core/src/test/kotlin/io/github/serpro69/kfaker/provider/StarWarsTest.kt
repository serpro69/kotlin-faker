package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.FakerService
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldNotBe

class StarWarsTest : DescribeSpec({
    describe("StarWars provider") {
        val starWars = StarWars(FakerService(faker = Faker()))

        context("any quote") {
            it("should return any quote") {
                starWars.quote() shouldNotBe null
            }
        }

        context("quotes filter") {
            it("should return a Admiral Ackbar quote") {
                starWars.quotes("admiral_ackbar") shouldNotBe null
            }
        }
    }
})
