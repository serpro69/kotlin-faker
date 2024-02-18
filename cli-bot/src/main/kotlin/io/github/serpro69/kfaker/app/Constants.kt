package io.github.serpro69.kfaker.app

import io.github.serpro69.kfaker.AbstractFaker
import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.FakerConfig
import io.github.serpro69.kfaker.books.BooksFaker
import io.github.serpro69.kfaker.commerce.CommerceFaker
import io.github.serpro69.kfaker.creatures.CreaturesFaker
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

val fakers: (config: FakerConfig) -> List<AbstractFaker> = {
    listOf(
        Faker(it),
        BooksFaker(it),
        CommerceFaker(it),
        CreaturesFaker(it),
        EduFaker(it),
        GamesFaker(it),
        HumorFaker(it),
        JapaneseMediaFaker(it),
        LoremFaker(it),
        MiscFaker(it),
        MoviesFaker(it),
        MusicFaker(it),
        SportsFaker(it),
        TechFaker(it),
        TravelFaker(it),
        TvShowsFaker(it),
    )
}
