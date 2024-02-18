package io.github.serpro69.kfaker.movies.provider

import io.github.serpro69.kfaker.movies.MoviesFaker
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldNotBe

class StarWarsTest : DescribeSpec({
    describe("StarWars provider") {
        // TODO is there a better way to test a provider directly w/o exposing constructors of FakerService?
        //  StarWars(FakerService(MoviesFaker()) won't work because the constructor in FakerService is internal,
        //  and we should try not to expose it to public
        //  We could expose FakerService via custom AbstractFaker implementation (e.g. see edu/FakerServiceTest)
        val starWars = MoviesFaker().starWars

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
