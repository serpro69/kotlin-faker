package io.github.serpro69.kfaker.creatures

import io.github.serpro69.kfaker.AbstractFaker
import io.github.serpro69.kfaker.FakerConfig
import io.github.serpro69.kfaker.FakerDsl
import io.github.serpro69.kfaker.creatures.provider.Ancient
import io.github.serpro69.kfaker.creatures.provider.Animal
import io.github.serpro69.kfaker.creatures.provider.Bird
import io.github.serpro69.kfaker.creatures.provider.Cat
import io.github.serpro69.kfaker.creatures.provider.Dog
import io.github.serpro69.kfaker.creatures.provider.Horse
import io.github.serpro69.kfaker.fakerConfig

/** Typealias for the [CreaturesFaker] */
typealias Faker = CreaturesFaker

/**
 * Provides access to fake data generators within the Creature domain.
 *
 * Each category (generator) from this [CreaturesFaker] is represented by a property that (usually)
 * has the same name as the `.yml` dictionary file.
 *
 * @property unique global provider for generation of unique values.
 */
@Suppress("unused")
class CreaturesFaker @JvmOverloads constructor(config: FakerConfig = fakerConfig {}) :
    AbstractFaker(config) {

    val ancient: Ancient by lazy { Ancient(fakerService) }
    val animal: Animal by lazy { Animal(fakerService) }
    val bird: Bird by lazy { Bird(fakerService) }
    val cat: Cat by lazy { Cat(fakerService) }
    val dog: Dog by lazy { Dog(fakerService) }
    val horse: Horse by lazy { Horse(fakerService) }

    @FakerDsl
    /** DSL builder for creating instances of [Faker] */
    class Builder internal constructor() : AbstractFaker.Builder<Faker>() {

        /** Builds an instance of [Faker] with this [config]. */
        override fun build(): Faker = Faker(config)
    }
}

/**
 * Applies the [block] function to [CreaturesFaker.Builder] and returns as an instance of
 * [CreaturesFaker] from that builder.
 */
fun faker(block: CreaturesFaker.Builder.() -> Unit): CreaturesFaker =
    CreaturesFaker.Builder().apply(block).build()
