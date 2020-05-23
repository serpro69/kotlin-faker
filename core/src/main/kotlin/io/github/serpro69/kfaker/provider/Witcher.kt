package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.GAMES] category.
 */
@Suppress("unused")
class Witcher internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Witcher>(fakerService) {
    override val categoryName = CategoryName.GAMES
    override val localUniqueDataProvider = LocalUniqueDataProvider<Witcher>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun characters() = resolve("witcher", "characters")
    fun witchers() = resolve("witcher", "witchers")
    fun schools() = resolve("witcher", "schools")
    fun locations() = resolve("witcher", "locations")
    fun quotes() = resolve("witcher", "quotes")
    fun monsters() = resolve("witcher", "monsters")
}
