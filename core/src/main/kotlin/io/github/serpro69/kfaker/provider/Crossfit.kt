package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.CROSSFIT] category.
 */
class Crossfit internal constructor(fakerService: FakerService): AbstractFakeDataProvider<Crossfit>(fakerService){
    override val category= YamlCategory.CROSSFIT
    override val localUniqueDataProvider= LocalUniqueDataProvider<Crossfit>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun competitions() = resolve("competition")
    fun maleAthletes() = resolve("athlete", "male")
    fun femaleAthletes() = resolve("athlete", "female")
    fun movements() = resolve("movement")
    fun girlWorkouts() = resolve("workout", "girl")
    fun heroWorkouts() = resolve("workout", "hero")
}
