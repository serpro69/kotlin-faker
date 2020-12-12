package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [CategoryName.AQUA_TEEN_HUNGER_FORCE] category.
 */
@Suppress("unused")
class AquaTeenHungerForce internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<AquaTeenHungerForce>(fakerService) {
    override val categoryName = CategoryName.AQUA_TEEN_HUNGER_FORCE
    override val localUniqueDataProvider = LocalUniqueDataProvider<AquaTeenHungerForce>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun character() = resolve("character")
    fun quote() = resolve("quote")
}