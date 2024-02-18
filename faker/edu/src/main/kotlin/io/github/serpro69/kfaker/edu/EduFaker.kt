package io.github.serpro69.kfaker.edu

import io.github.serpro69.kfaker.AbstractFaker
import io.github.serpro69.kfaker.FakerConfig
import io.github.serpro69.kfaker.FakerDsl
import io.github.serpro69.kfaker.edu.provider.Educator
import io.github.serpro69.kfaker.edu.provider.Job
import io.github.serpro69.kfaker.edu.provider.Science
import io.github.serpro69.kfaker.edu.provider.University
import io.github.serpro69.kfaker.fakerConfig

/**
 * Typealias for the [EduFaker]
 */
typealias Faker = EduFaker

/**
 * Provides access to fake data generators within the Education domain.
 *
 * Each category (generator) from this [EduFaker] is represented by a property
 * that (usually) has the same name as the `.yml` dictionary file.
 *
 * @property unique global provider for generation of unique values.
 */
@Suppress("unused")
class EduFaker @JvmOverloads constructor(config: FakerConfig = fakerConfig { }) : AbstractFaker(config) {

    val job: Job by lazy { Job(fakerService) }
    val educator: Educator by lazy { Educator(fakerService) }
    val science: Science by lazy { Science(fakerService) }
    val university: University by lazy { University(fakerService) }

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
 * Applies the [block] function to [EduFaker.Builder]
 * and returns as an instance of [EduFaker] from that builder.
 */
fun faker(block: EduFaker.Builder.() -> Unit): EduFaker = EduFaker.Builder().apply(block).build()
