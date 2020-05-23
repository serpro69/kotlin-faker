package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.GAMES] category.
 */
@Suppress("unused")
class Pokemon internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Pokemon>(fakerService) {
    override val categoryName = CategoryName.GAMES
    override val localUniqueDataProvider = LocalUniqueDataProvider<Pokemon>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun names() = resolve("pokemon", "names")
    fun locations() = resolve("pokemon", "locations")
    fun moves() = resolve("pokemon", "moves")
}
