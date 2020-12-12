package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [CategoryName.GAME] category.
 */
@Suppress("unused")
class Game internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Game>(fakerService) {
    override val categoryName = CategoryName.GAME
    override val localUniqueDataProvider = LocalUniqueDataProvider<Game>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun title() = resolve("title")
}
