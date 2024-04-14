@file:FakerArb(
    Faker::class,
    BooksFaker::class,
    CommerceFaker::class,
    CreaturesFaker::class,
    DatabasesFaker::class,
    EduFaker::class,
    GamesFaker::class,
    HumorFaker::class,
    JapaneseMediaFaker::class,
    LoremFaker::class,
    MiscFaker::class,
    MoviesFaker::class,
    MusicFaker::class,
    SportsFaker::class,
    TechFaker::class,
    TravelFaker::class,
    TvShowsFaker::class,
)

package io.github.serpro69.kfaker.kotest

import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.books.BooksFaker
import io.github.serpro69.kfaker.commerce.CommerceFaker
import io.github.serpro69.kfaker.creatures.CreaturesFaker
import io.github.serpro69.kfaker.databases.DatabasesFaker
import io.github.serpro69.kfaker.edu.EduFaker
import io.github.serpro69.kfaker.games.GamesFaker
import io.github.serpro69.kfaker.humor.HumorFaker
import io.github.serpro69.kfaker.japmedia.JapaneseMediaFaker
import io.github.serpro69.kfaker.lorem.LoremFaker
import io.github.serpro69.kfaker.misc.MiscFaker
import io.github.serpro69.kfaker.movies.MoviesFaker
import io.github.serpro69.kfaker.music.MusicFaker
import io.github.serpro69.kfaker.sports.SportsFaker
import io.github.serpro69.kfaker.tech.TechFaker
import io.github.serpro69.kfaker.travel.TravelFaker
import io.github.serpro69.kfaker.tv.TvShowsFaker
import io.kotest.core.spec.style.DescribeSpec

class KotestArbProviderIT : DescribeSpec({

    it("should generate arb extensions for each faker") {
        // test code generation can compile, nothing else
    }
})
