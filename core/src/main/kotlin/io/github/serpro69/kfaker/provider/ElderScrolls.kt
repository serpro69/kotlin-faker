package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.GAMES] category.
 */
@Suppress("unused")
class ElderScrolls internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<ElderScrolls>(fakerService) {
    override val categoryName = CategoryName.GAMES
    override val localUniqueDataProvider = LocalUniqueDataProvider<ElderScrolls>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun race() = resolve("elder_scrolls", "race")
    fun creature() = resolve("elder_scrolls", "creature")
    fun region() = resolve("elder_scrolls", "region")
    fun dragon() = resolve("elder_scrolls", "dragon")
    fun city() = resolve("elder_scrolls", "city")
    fun firstName() = resolve("elder_scrolls", "first_name")
    fun lastName() = resolve("elder_scrolls", "last_name")
}
