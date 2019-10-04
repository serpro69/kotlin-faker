package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.ESPORT] category.
 */
@Suppress("unused")
class ESport internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<ESport>(fakerService) {
    override val categoryName = CategoryName.ESPORT
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val players = resolve("players")
    val teams = resolve("teams")
    val events = resolve("events")
    val leagues = resolve("leagues")
    val games = resolve("games")
}
