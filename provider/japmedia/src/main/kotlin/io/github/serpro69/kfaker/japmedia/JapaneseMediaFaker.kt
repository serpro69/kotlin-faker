package io.github.serpro69.kfaker.japmedia

import io.github.serpro69.kfaker.AbstractFaker
import io.github.serpro69.kfaker.FakerConfig
import io.github.serpro69.kfaker.FakerDsl
import io.github.serpro69.kfaker.fakerConfig
import io.github.serpro69.kfaker.japmedia.provider.Conan
import io.github.serpro69.kfaker.japmedia.provider.CowboyBebop
import io.github.serpro69.kfaker.japmedia.provider.Doraemon
import io.github.serpro69.kfaker.japmedia.provider.DragonBall
import io.github.serpro69.kfaker.japmedia.provider.FmaBrotherhood
import io.github.serpro69.kfaker.japmedia.provider.Naruto
import io.github.serpro69.kfaker.japmedia.provider.OnePiece
import io.github.serpro69.kfaker.japmedia.provider.StudioGhibli
import io.github.serpro69.kfaker.japmedia.provider.SwordArtOnline

/**
 * Typealias for the [JapaneseMediaFaker]
 */
typealias Faker = JapaneseMediaFaker

/**
 * Provides access to fake data generators within the JapaneseMedia domain.
 *
 * Each category (generator) from this [JapaneseMediaFaker] is represented by a property
 * that (usually) has the same name as the `.yml` dictionary file.
 *
 * @property unique global provider for generation of unique values.
 */
@Suppress("unused")
class JapaneseMediaFaker @JvmOverloads constructor(config: FakerConfig = fakerConfig { }): AbstractFaker(config) {

    val conan: Conan by lazy { Conan(fakerService) }
    val cowboyBebop: CowboyBebop by lazy { CowboyBebop(fakerService) }
    val doraemon: Doraemon by lazy { Doraemon(fakerService) }
    val dragonBall: DragonBall by lazy { DragonBall(fakerService) }
    val fmaBrotherhood: FmaBrotherhood by lazy { FmaBrotherhood(fakerService) }
    val naruto: Naruto by lazy { Naruto(fakerService) }
    val onePiece: OnePiece by lazy { OnePiece(fakerService) }
    val studioGhibli: StudioGhibli by lazy { StudioGhibli(fakerService) }
    val swordArtOnline: SwordArtOnline by lazy { SwordArtOnline(fakerService) }

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
 * Applies the [block] function to [JapaneseMediaFaker.Builder]
 * and returns as an instance of [JapaneseMediaFaker] from that builder.
 */
fun faker(block: JapaneseMediaFaker.Builder.() -> Unit): JapaneseMediaFaker = JapaneseMediaFaker.Builder().apply(block).build()
