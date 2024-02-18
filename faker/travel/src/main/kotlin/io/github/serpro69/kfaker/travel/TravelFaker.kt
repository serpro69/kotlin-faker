package io.github.serpro69.kfaker.travel

import io.github.serpro69.kfaker.AbstractFaker
import io.github.serpro69.kfaker.FakerConfig
import io.github.serpro69.kfaker.FakerDsl
import io.github.serpro69.kfaker.fakerConfig
import io.github.serpro69.kfaker.travel.provider.Airport
import io.github.serpro69.kfaker.travel.provider.Australia
import io.github.serpro69.kfaker.travel.provider.Mountain
import io.github.serpro69.kfaker.travel.provider.Nation
import io.github.serpro69.kfaker.travel.provider.TrainStation

/**
 * Typealias for the [TravelFaker]
 */
typealias Faker = TravelFaker

/**
 * Provides access to fake data generators within the Travel domain.
 *
 * Each category (generator) from this [TravelFaker] is represented by a property
 * that (usually) has the same name as the `.yml` dictionary file.
 *
 * @property unique global provider for generation of unique values.
 */
@Suppress("unused")
class TravelFaker @JvmOverloads constructor(config: FakerConfig = fakerConfig { }) : AbstractFaker(config) {

    val airport: Airport by lazy { Airport(fakerService) }
    val australia: Australia by lazy { Australia(fakerService) }
    // TODO val compass: Compass by lazy {Compass(fakerService) }
    val mountain: Mountain by lazy { Mountain(fakerService) }
    val nation: Nation by lazy { Nation(fakerService) }
    val trainStation: TrainStation by lazy { TrainStation(fakerService) }

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
 * Applies the [block] function to [TravelFaker.Builder]
 * and returns as an instance of [TravelFaker] from that builder.
 */
fun faker(block: TravelFaker.Builder.() -> Unit): TravelFaker = TravelFaker.Builder().apply(block).build()
