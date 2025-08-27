package io.github.serpro69.kfaker.humor

import io.github.serpro69.kfaker.AbstractFaker
import io.github.serpro69.kfaker.FakerConfig
import io.github.serpro69.kfaker.FakerDsl
import io.github.serpro69.kfaker.fakerConfig
import io.github.serpro69.kfaker.humor.provider.Chiquito
import io.github.serpro69.kfaker.humor.provider.ChuckNorris
import io.github.serpro69.kfaker.humor.provider.FunnyName
import io.github.serpro69.kfaker.humor.provider.JackHandey
import io.github.serpro69.kfaker.humor.provider.MitchHedberg

/** Typealias for the [HumorFaker] */
typealias Faker = HumorFaker

/**
 * Provides access to fake data generators within the Humor domain.
 *
 * Each category (generator) from this [HumorFaker] is represented by a property that (usually) has
 * the same name as the `.yml` dictionary file.
 *
 * @property unique global provider for generation of unique values.
 */
@Suppress("unused")
class HumorFaker @JvmOverloads constructor(config: FakerConfig = fakerConfig {}) :
    AbstractFaker(config) {

    val chiquito: Chiquito by lazy { Chiquito(fakerService) }
    val chuckNorris: ChuckNorris by lazy { ChuckNorris(fakerService) }
    val funnyName: FunnyName by lazy { FunnyName(fakerService) }
    val jackHandey: JackHandey by lazy { JackHandey(fakerService) }
    val mitchHedberg: MitchHedberg by lazy { MitchHedberg(fakerService) }

    @FakerDsl
    /** DSL builder for creating instances of [Faker] */
    class Builder internal constructor() : AbstractFaker.Builder<Faker>() {

        /** Builds an instance of [Faker] with this [config]. */
        override fun build(): Faker = Faker(config)
    }
}

/**
 * Applies the [block] function to [HumorFaker.Builder] and returns as an instance of [HumorFaker]
 * from that builder.
 */
fun faker(block: HumorFaker.Builder.() -> Unit): HumorFaker =
    HumorFaker.Builder().apply(block).build()
