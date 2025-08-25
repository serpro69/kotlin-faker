package io.github.serpro69.kfaker.misc

import io.github.serpro69.kfaker.AbstractFaker
import io.github.serpro69.kfaker.Faker as CoreFaker
import io.github.serpro69.kfaker.FakerConfig
import io.github.serpro69.kfaker.FakerDsl
import io.github.serpro69.kfaker.fakerConfig
import io.github.serpro69.kfaker.misc.provider.Artist
import io.github.serpro69.kfaker.misc.provider.Blood
import io.github.serpro69.kfaker.misc.provider.Demographic
import io.github.serpro69.kfaker.misc.provider.DrivingLicense
import io.github.serpro69.kfaker.misc.provider.GreekPhilosophers
import io.github.serpro69.kfaker.misc.provider.Hobby
import io.github.serpro69.kfaker.misc.provider.Military
import io.github.serpro69.kfaker.misc.provider.Relationship

/** Typealias for the [MiscFaker] */
typealias Faker = MiscFaker

/**
 * Provides access to fake data generators that don't belong in the [CoreFaker] but also don't fit
 * into any of the existing providers.
 *
 * Each category (generator) from this [MiscFaker] is represented by a property that (usually) has
 * the same name as the `.yml` dictionary file.
 *
 * @property unique global provider for generation of unique values.
 */
@Suppress("unused")
class MiscFaker @JvmOverloads constructor(config: FakerConfig = fakerConfig {}) :
    AbstractFaker(config) {

    val artist: Artist by lazy { Artist(fakerService) }
    val blood: Blood by lazy { Blood(fakerService) }
    val demographic: Demographic by lazy { Demographic(fakerService) }
    val drivingLicense: DrivingLicense by lazy { DrivingLicense(fakerService) }
    val greekPhilosophers: GreekPhilosophers by lazy { GreekPhilosophers(fakerService) }
    val hobby: Hobby by lazy { Hobby(fakerService) }
    val military: Military by lazy { Military(fakerService) }
    val relationship: Relationship by lazy { Relationship(fakerService) }

    @FakerDsl
    /** DSL builder for creating instances of [Faker] */
    class Builder internal constructor() : AbstractFaker.Builder<Faker>() {

        /** Builds an instance of [Faker] with this [config]. */
        override fun build(): Faker = Faker(config)
    }
}

/**
 * Applies the [block] function to [MiscFaker.Builder] and returns as an instance of [MiscFaker]
 * from that builder.
 */
fun faker(block: MiscFaker.Builder.() -> Unit): MiscFaker = MiscFaker.Builder().apply(block).build()
