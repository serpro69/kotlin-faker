package io.github.serpro69.kfaker.games

import io.github.serpro69.kfaker.AbstractFaker
import io.github.serpro69.kfaker.FakerConfig
import io.github.serpro69.kfaker.FakerDsl
import io.github.serpro69.kfaker.fakerConfig
import io.github.serpro69.kfaker.games.provider.ClashOfClans
import io.github.serpro69.kfaker.games.provider.Control
import io.github.serpro69.kfaker.games.provider.DnD
import io.github.serpro69.kfaker.games.provider.Dota
import io.github.serpro69.kfaker.games.provider.ElderScrolls
import io.github.serpro69.kfaker.games.provider.Fallout
import io.github.serpro69.kfaker.games.provider.FinalFantasyXIV
import io.github.serpro69.kfaker.games.provider.Game
import io.github.serpro69.kfaker.games.provider.HalfLife
import io.github.serpro69.kfaker.games.provider.Heroes
import io.github.serpro69.kfaker.games.provider.HeroesOfTheStorm
import io.github.serpro69.kfaker.games.provider.LeagueOfLegends
import io.github.serpro69.kfaker.games.provider.Minecraft
import io.github.serpro69.kfaker.games.provider.Myst
import io.github.serpro69.kfaker.games.provider.Overwatch
import io.github.serpro69.kfaker.games.provider.Pokemon
import io.github.serpro69.kfaker.games.provider.SonicTheHedgehog
import io.github.serpro69.kfaker.games.provider.StreetFighter
import io.github.serpro69.kfaker.games.provider.SuperMario
import io.github.serpro69.kfaker.games.provider.SuperSmashBros
import io.github.serpro69.kfaker.games.provider.Touhou
import io.github.serpro69.kfaker.games.provider.WarhammerFantasy
import io.github.serpro69.kfaker.games.provider.Witcher
import io.github.serpro69.kfaker.games.provider.WorldOfWarcraft
import io.github.serpro69.kfaker.games.provider.Zelda

/**
 * Typealias for the [GamesFaker]
 */
typealias Faker = GamesFaker

/**
 * Provides access to fake data generators withing the Books domain.
 *
 * Each category (generator) from this [GamesFaker] is represented by a property that has the same name as the `.yml` file.
 *
 * @property unique global provider for generation of unique values.
 */
@Suppress("unused")
class GamesFaker @JvmOverloads constructor(config: FakerConfig = fakerConfig { }): AbstractFaker(config) {

    val clashOfClans: ClashOfClans by lazy { ClashOfClans(fakerService) }
    val control: Control by lazy { Control(fakerService) }
    val dnd: DnD by lazy { DnD(fakerService) }
    val dota: Dota by lazy { Dota(fakerService) }
    val elderScrolls: ElderScrolls by lazy { ElderScrolls(fakerService) }
    val fallout: Fallout by lazy { Fallout(fakerService) }
    val finalFantasyXIV: FinalFantasyXIV by lazy { FinalFantasyXIV(fakerService) }
    val game: Game by lazy { Game(fakerService) }
    val halfLife: HalfLife by lazy { HalfLife(fakerService) }
    val heroes: Heroes by lazy { Heroes(fakerService) }
    val heroesOfTheStorm: HeroesOfTheStorm by lazy { HeroesOfTheStorm(fakerService) }
    val leagueOfLegends: LeagueOfLegends by lazy { LeagueOfLegends(fakerService) }
    val minecraft: Minecraft by lazy { Minecraft(fakerService) }
    val myst: Myst by lazy { Myst(fakerService) }
    val overwatch: Overwatch by lazy { Overwatch(fakerService) }
    val pokemon: Pokemon by lazy { Pokemon(fakerService) }
    val sonicTheHedgehog: SonicTheHedgehog by lazy { SonicTheHedgehog(fakerService) }
    val streetFighter: StreetFighter by lazy { StreetFighter(fakerService) }
    val superMario: SuperMario by lazy { SuperMario(fakerService) }
    val superSmashBros: SuperSmashBros by lazy { SuperSmashBros(fakerService) }
    val touhou: Touhou by lazy { Touhou(fakerService) }
    val warhammerFantasy: WarhammerFantasy by lazy { WarhammerFantasy(fakerService) }
    val witcher: Witcher by lazy { Witcher(fakerService) }
    val worldOfWarcraft: WorldOfWarcraft by lazy { WorldOfWarcraft(fakerService) }
    val zelda: Zelda by lazy { Zelda(fakerService) }

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
 * Applies the [block] function to [GamesFaker.Builder]
 * and returns as an instance of [GamesFaker] from that builder.
 */
fun faker(block: GamesFaker.Builder.() -> Unit): GamesFaker = GamesFaker.Builder().apply(block).build()
