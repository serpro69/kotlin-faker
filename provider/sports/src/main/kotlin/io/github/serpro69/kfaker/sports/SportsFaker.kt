package io.github.serpro69.kfaker.sports

import io.github.serpro69.kfaker.AbstractFaker
import io.github.serpro69.kfaker.FakerConfig
import io.github.serpro69.kfaker.FakerDsl
import io.github.serpro69.kfaker.fakerConfig
import io.github.serpro69.kfaker.sports.provider.Basketball
import io.github.serpro69.kfaker.sports.provider.Chess
import io.github.serpro69.kfaker.sports.provider.Crossfit
import io.github.serpro69.kfaker.sports.provider.ESport
import io.github.serpro69.kfaker.sports.provider.Football
import io.github.serpro69.kfaker.sports.provider.Mountaineering
import io.github.serpro69.kfaker.sports.provider.Volleyball
import io.github.serpro69.kfaker.sports.provider.WorldCup

/**
 * Typealias for the [SportsFaker]
 */
typealias Faker = SportsFaker

/**
 * Provides access to fake data generators withing the Books domain.
 *
 * Each category (generator) from this [SportsFaker] is represented by a property that has the same name as the `.yml` file.
 *
 * @property unique global provider for generation of unique values.
 */
@Suppress("unused")
class SportsFaker @JvmOverloads constructor(config: FakerConfig = fakerConfig { }): AbstractFaker(config) {

    val basketball: Basketball by lazy { Basketball(fakerService) }
    val chess: Chess by lazy { Chess(fakerService) }
    val crossfit: Crossfit by lazy { Crossfit(fakerService) }
    val eSport: ESport by lazy { ESport(fakerService) }
    val football: Football by lazy { Football(fakerService) }
    val mountaineering: Mountaineering by lazy { Mountaineering(fakerService) }
    val volleyball: Volleyball by lazy { Volleyball(fakerService) }
    val worldCup: WorldCup by lazy { WorldCup(fakerService) }

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
 * Applies the [block] function to [SportsFaker.Builder]
 * and returns as an instance of [SportsFaker] from that builder.
 */
fun faker(block: SportsFaker.Builder.() -> Unit): SportsFaker = SportsFaker.Builder().apply(block).build()
