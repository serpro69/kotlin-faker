package io.github.serpro69.kfaker.lorem

import io.github.serpro69.kfaker.AbstractFaker
import io.github.serpro69.kfaker.FakerConfig
import io.github.serpro69.kfaker.FakerDsl
import io.github.serpro69.kfaker.fakerConfig
import io.github.serpro69.kfaker.lorem.provider.Adjective
import io.github.serpro69.kfaker.lorem.provider.Lorem
import io.github.serpro69.kfaker.lorem.provider.Markdown
import io.github.serpro69.kfaker.lorem.provider.NatoPhoneticAlphabet
import io.github.serpro69.kfaker.lorem.provider.Verbs

/**
 * Typealias for the [LoremFaker]
 */
typealias Faker = LoremFaker

/**
 * Provides access to fake data generators within the Lorem domain.
 *
 * Each category (generator) from this [LoremFaker] is represented by a property
 * that (usually) has the same name as the `.yml` dictionary file.
 *
 * @property unique global provider for generation of unique values.
 */
@Suppress("unused")
class LoremFaker @JvmOverloads constructor(config: FakerConfig = fakerConfig { }) : AbstractFaker(config) {

    val adjective: Adjective by lazy { Adjective(fakerService) }
    val lorem: Lorem by lazy { Lorem(fakerService) }
    val markdown: Markdown by lazy { Markdown(fakerService) }
    val natoPhoneticAlphabet: NatoPhoneticAlphabet by lazy { NatoPhoneticAlphabet(fakerService) }
    val verbs: Verbs by lazy { Verbs(fakerService) }

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
 * Applies the [block] function to [LoremFaker.Builder]
 * and returns as an instance of [LoremFaker] from that builder.
 */
fun faker(block: LoremFaker.Builder.() -> Unit): LoremFaker = LoremFaker.Builder().apply(block).build()
