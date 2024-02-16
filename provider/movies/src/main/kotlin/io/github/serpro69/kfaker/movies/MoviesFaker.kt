package io.github.serpro69.kfaker.movies

import io.github.serpro69.kfaker.AbstractFaker
import io.github.serpro69.kfaker.FakerConfig
import io.github.serpro69.kfaker.FakerDsl
import io.github.serpro69.kfaker.fakerConfig
import io.github.serpro69.kfaker.movies.provider.Avatar
import io.github.serpro69.kfaker.movies.provider.BackToTheFuture
import io.github.serpro69.kfaker.movies.provider.Departed
import io.github.serpro69.kfaker.movies.provider.DumbAndDumber
import io.github.serpro69.kfaker.movies.provider.GhostBusters
import io.github.serpro69.kfaker.movies.provider.HarryPotter
import io.github.serpro69.kfaker.movies.provider.HitchhikersGuideToTheGalaxy
import io.github.serpro69.kfaker.movies.provider.Hobbit
import io.github.serpro69.kfaker.movies.provider.HowToTrainYourDragon
import io.github.serpro69.kfaker.movies.provider.Lebowski
import io.github.serpro69.kfaker.movies.provider.LordOfTheRings
import io.github.serpro69.kfaker.movies.provider.Movie
import io.github.serpro69.kfaker.movies.provider.PrincessBride
import io.github.serpro69.kfaker.movies.provider.StarWars
import io.github.serpro69.kfaker.movies.provider.TheRoom
import io.github.serpro69.kfaker.movies.provider.Tron
import io.github.serpro69.kfaker.movies.provider.VForVendetta

/**
 * Typealias for the [MoviesFaker]
 */
typealias Faker = MoviesFaker

/**
 * Provides access to fake data generators withing the Books domain.
 *
 * Each category (generator) from this [MoviesFaker] is represented by a property that has the same name as the `.yml` file.
 *
 * @property unique global provider for generation of unique values.
 */
@Suppress("unused")
class MoviesFaker @JvmOverloads constructor(config: FakerConfig = fakerConfig { }): AbstractFaker(config) {

    val avatar: Avatar by lazy { Avatar(fakerService) }
    val backToTheFuture: BackToTheFuture by lazy { BackToTheFuture(fakerService) }
    val departed: Departed by lazy { Departed(fakerService) }
    val dumbAndDumber: DumbAndDumber by lazy { DumbAndDumber(fakerService) }
    val ghostBusters: GhostBusters by lazy { GhostBusters(fakerService) }
    val harryPotter: HarryPotter by lazy { HarryPotter(fakerService) }
    val hitchhikersGuideToTheGalaxy: HitchhikersGuideToTheGalaxy by lazy { HitchhikersGuideToTheGalaxy(fakerService) }
    val hobbit: Hobbit by lazy { Hobbit(fakerService) }
    val howToTrainYourDragon: HowToTrainYourDragon by lazy { HowToTrainYourDragon(fakerService) }
    val lebowski: Lebowski by lazy { Lebowski(fakerService) }
    val lordOfTheRings: LordOfTheRings by lazy { LordOfTheRings(fakerService) }
    val movie: Movie by lazy { Movie(fakerService) }
    val princessBride: PrincessBride by lazy { PrincessBride(fakerService) }
    val starWars: StarWars by lazy { StarWars(fakerService) }
    val theRoom: TheRoom by lazy { TheRoom(fakerService) }
    val tron: Tron by lazy { Tron(fakerService, randomService) }
    val vForVendetta: VForVendetta by lazy { VForVendetta(fakerService) }

    @FakerDsl
    /**
     * DSL builder for creating instances of [Faker]
     */
    class Builder internal constructor() : AbstractFaker.Builder<Faker>() {

        /**
         * Builds an instance of [Faker] with this [config].
         */
        override fun build(): Faker = Faker(config)
    }
}

/**
 * Applies the [block] function to [MoviesFaker.Builder]
 * and returns as an instance of [MoviesFaker] from that builder.
 */
fun faker(block: MoviesFaker.Builder.() -> Unit): MoviesFaker = MoviesFaker.Builder().apply(block).build()
