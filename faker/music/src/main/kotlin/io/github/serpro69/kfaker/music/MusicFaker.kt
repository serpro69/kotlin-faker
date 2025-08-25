package io.github.serpro69.kfaker.music

import io.github.serpro69.kfaker.AbstractFaker
import io.github.serpro69.kfaker.FakerConfig
import io.github.serpro69.kfaker.FakerDsl
import io.github.serpro69.kfaker.fakerConfig
import io.github.serpro69.kfaker.music.provider.BossaNova
import io.github.serpro69.kfaker.music.provider.GratefulDead
import io.github.serpro69.kfaker.music.provider.HipHop
import io.github.serpro69.kfaker.music.provider.KPop
import io.github.serpro69.kfaker.music.provider.Music
import io.github.serpro69.kfaker.music.provider.Opera
import io.github.serpro69.kfaker.music.provider.PearlJam
import io.github.serpro69.kfaker.music.provider.Phish
import io.github.serpro69.kfaker.music.provider.Prince
import io.github.serpro69.kfaker.music.provider.RockBand
import io.github.serpro69.kfaker.music.provider.Rush
import io.github.serpro69.kfaker.music.provider.SmashingPumpkins
import io.github.serpro69.kfaker.music.provider.Theater
import io.github.serpro69.kfaker.music.provider.UmphreysMcgee

/** Typealias for the [MusicFaker] */
typealias Faker = MusicFaker

/**
 * Provides access to fake data generators within the Music domain.
 *
 * Each category (generator) from this [MusicFaker] is represented by a property that (usually) has
 * the same name as the `.yml` dictionary file.
 *
 * @property unique global provider for generation of unique values.
 */
@Suppress("unused")
class MusicFaker @JvmOverloads constructor(config: FakerConfig = fakerConfig {}) :
    AbstractFaker(config) {

    val bossaNova: BossaNova by lazy { BossaNova(fakerService) }
    val gratefulDead: GratefulDead by lazy { GratefulDead(fakerService) }
    val hipHop: HipHop by lazy { HipHop(fakerService) }
    val kPop: KPop by lazy { KPop(fakerService) }
    val music: Music by lazy { Music(fakerService) }
    val opera: Opera by lazy { Opera(fakerService) }
    val pearlJam: PearlJam by lazy { PearlJam(fakerService) }
    val phish: Phish by lazy { Phish(fakerService) }
    val prince: Prince by lazy { Prince(fakerService) }
    val rockBand: RockBand by lazy { RockBand(fakerService) }
    val rush: Rush by lazy { Rush(fakerService) }
    val theater: Theater by lazy { Theater(fakerService) }
    val smashingPumpkins: SmashingPumpkins by lazy { SmashingPumpkins(fakerService) }
    val umphreysMcgee: UmphreysMcgee by lazy { UmphreysMcgee(fakerService) }

    @Deprecated(
        level = DeprecationLevel.WARNING,
        message = "Will be removed in 2.0, use 'theater' instead",
        replaceWith = ReplaceWith("theater"),
    )
    val show: Theater by lazy { Theater(fakerService) }

    @FakerDsl
    /** DSL builder for creating instances of [Faker] */
    class Builder internal constructor() : AbstractFaker.Builder<Faker>() {

        /** Builds an instance of [Faker] with this [config]. */
        override fun build(): Faker = Faker(config)
    }
}

/**
 * Applies the [block] function to [MusicFaker.Builder] and returns as an instance of [MusicFaker]
 * from that builder.
 */
fun faker(block: MusicFaker.Builder.() -> Unit): MusicFaker =
    MusicFaker.Builder().apply(block).build()
