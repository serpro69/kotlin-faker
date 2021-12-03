package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.FakerService
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldNotBe

class CrossfitTest : DescribeSpec({
    describe("Crossfit provider") {
        val crossfit = Crossfit( FakerService(faker = Faker()))

        context("competition fun") {
            it("should returns a Crossfit® competition name") {
                crossfit.competitions() shouldNotBe null
            }
        }
    }
})
