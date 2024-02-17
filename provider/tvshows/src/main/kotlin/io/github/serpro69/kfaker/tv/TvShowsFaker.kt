package io.github.serpro69.kfaker.tv

import io.github.serpro69.kfaker.AbstractFaker
import io.github.serpro69.kfaker.FakerConfig
import io.github.serpro69.kfaker.FakerDsl
import io.github.serpro69.kfaker.fakerConfig
import io.github.serpro69.kfaker.tv.provider.AquaTeenHungerForce
import io.github.serpro69.kfaker.tv.provider.Archer
import io.github.serpro69.kfaker.tv.provider.BigBangTheory
import io.github.serpro69.kfaker.tv.provider.BojackHorseman
import io.github.serpro69.kfaker.tv.provider.BreakingBad
import io.github.serpro69.kfaker.tv.provider.BrooklynNineNine
import io.github.serpro69.kfaker.tv.provider.Buffy
import io.github.serpro69.kfaker.tv.provider.Community
import io.github.serpro69.kfaker.tv.provider.DrWho
import io.github.serpro69.kfaker.tv.provider.FamilyGuy
import io.github.serpro69.kfaker.tv.provider.FinalSpace
import io.github.serpro69.kfaker.tv.provider.FreshPriceOfBelAir
import io.github.serpro69.kfaker.tv.provider.Friends
import io.github.serpro69.kfaker.tv.provider.Futurama
import io.github.serpro69.kfaker.tv.provider.GameOfThrones
import io.github.serpro69.kfaker.tv.provider.HeyArnold
import io.github.serpro69.kfaker.tv.provider.HowIMetYourMother
import io.github.serpro69.kfaker.tv.provider.MichaelScott
import io.github.serpro69.kfaker.tv.provider.NewGirl
import io.github.serpro69.kfaker.tv.provider.ParksAndRec
import io.github.serpro69.kfaker.tv.provider.RickAndMorty
import io.github.serpro69.kfaker.tv.provider.Rupaul
import io.github.serpro69.kfaker.tv.provider.Seinfeld
import io.github.serpro69.kfaker.tv.provider.SiliconValley
import io.github.serpro69.kfaker.tv.provider.Simpsons
import io.github.serpro69.kfaker.tv.provider.SouthPark
import io.github.serpro69.kfaker.tv.provider.Spongebob
import io.github.serpro69.kfaker.tv.provider.StarTrek
import io.github.serpro69.kfaker.tv.provider.Stargate
import io.github.serpro69.kfaker.tv.provider.StrangerThings
import io.github.serpro69.kfaker.tv.provider.Suits
import io.github.serpro69.kfaker.tv.provider.Supernatural
import io.github.serpro69.kfaker.tv.provider.TheExpanse
import io.github.serpro69.kfaker.tv.provider.TheITCrowd
import io.github.serpro69.kfaker.tv.provider.TheOffice
import io.github.serpro69.kfaker.tv.provider.TheThickOfIt
import io.github.serpro69.kfaker.tv.provider.TwinPeaks
import io.github.serpro69.kfaker.tv.provider.VentureBros

/**
 * Typealias for the [TvShowsFaker]
 */
typealias Faker = TvShowsFaker

/**
 * Provides access to fake data generators within the TvShows domain.
 *
 * Each category (generator) from this [TvShowsFaker] is represented by a property
 * that (usually) has the same name as the `.yml` dictionary file.
 *
 * @property unique global provider for generation of unique values.
 */
@Suppress("unused")
class TvShowsFaker @JvmOverloads constructor(config: FakerConfig = fakerConfig { }): AbstractFaker(config) {

    val aquaTeenHungerForce: AquaTeenHungerForce by lazy { AquaTeenHungerForce(fakerService) }
    val archer: Archer by lazy { Archer(fakerService) }
    val bigBangTheory: BigBangTheory by lazy { BigBangTheory(fakerService) }
    val bojackHorseman: BojackHorseman by lazy { BojackHorseman(fakerService) }
    val breakingBad: BreakingBad by lazy { BreakingBad(fakerService) }
    val brooklynNineNine: BrooklynNineNine by lazy { BrooklynNineNine(fakerService) }
    val buffy: Buffy by lazy { Buffy(fakerService) }
    val community: Community by lazy { Community(fakerService) }
    val drWho: DrWho by lazy { DrWho(fakerService) }
    val familyGuy: FamilyGuy by lazy { FamilyGuy(fakerService) }
    val finalSpace: FinalSpace by lazy { FinalSpace(fakerService) }
    val freshPriceOfBelAir: FreshPriceOfBelAir by lazy { FreshPriceOfBelAir(fakerService) }
    val friends: Friends by lazy { Friends(fakerService) }
    val futurama: Futurama by lazy { Futurama(fakerService) }
    val gameOfThrones: GameOfThrones by lazy { GameOfThrones(fakerService) }
    val heyArnold: HeyArnold by lazy { HeyArnold(fakerService) }
    val howIMetYourMother: HowIMetYourMother by lazy { HowIMetYourMother(fakerService) }
    val michaelScott: MichaelScott by lazy { MichaelScott(fakerService) }
    val newGirl: NewGirl by lazy { NewGirl(fakerService) }
    val parksAndRec: ParksAndRec by lazy { ParksAndRec(fakerService) }
    val rickAndMorty: RickAndMorty by lazy { RickAndMorty(fakerService) }
    val rupaul: Rupaul by lazy { Rupaul(fakerService) }
    val seinfeld: Seinfeld by lazy { Seinfeld(fakerService) }
    val siliconValley: SiliconValley by lazy { SiliconValley(fakerService) }
    val simpsons: Simpsons by lazy { Simpsons(fakerService) }
    val southPark: SouthPark by lazy { SouthPark(fakerService) }
    val spongebob: Spongebob by lazy { Spongebob(fakerService) }
    val stargate: Stargate by lazy { Stargate(fakerService) }
    val starTrek: StarTrek by lazy { StarTrek(fakerService) }
    val strangerThings: StrangerThings by lazy { StrangerThings(fakerService) }
    val suits: Suits by lazy { Suits(fakerService) }
    val supernatural: Supernatural by lazy { Supernatural(fakerService) }
    val theExpanse: TheExpanse by lazy { TheExpanse(fakerService) }
    val theITCrowd: TheITCrowd by lazy { TheITCrowd(fakerService) }
    val theOffice: TheOffice by lazy { TheOffice(fakerService) }
    val theThickOfIt: TheThickOfIt by lazy { TheThickOfIt(fakerService) }
    val twinPeaks: TwinPeaks by lazy { TwinPeaks(fakerService) }
    val ventureBros: VentureBros by lazy { VentureBros(fakerService) }

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
 * Applies the [block] function to [TvShowsFaker.Builder]
 * and returns as an instance of [TvShowsFaker] from that builder.
 */
fun faker(block: TvShowsFaker.Builder.() -> Unit): TvShowsFaker = TvShowsFaker.Builder().apply(block).build()
