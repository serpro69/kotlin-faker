package io.github.serpro69.kfaker.sports.provider

import io.github.serpro69.kfaker.sports.SportsFaker
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldNotBe

class CrossfitTest : DescribeSpec({
    describe("Crossfit provider") {
        // TODO is there a better way to test a provider directly w/o exposing constructors of FakerService?
        //  Crossfit(FakerService(SportsFaker()) won't work because the constructor in FakerService is internal,
        //  and we should try not to expose it to public
        val crossfit = SportsFaker().crossfit

        context("competition fun") {
            it("should return a Crossfit® competition name") {
                crossfit.competitions() shouldNotBe null
            }
        }
    }
})
