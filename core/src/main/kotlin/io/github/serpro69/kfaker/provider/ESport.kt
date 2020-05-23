package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.ESPORT] category.
 */
@Suppress("unused")
class ESport internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<ESport>(fakerService) {
    override val categoryName = CategoryName.ESPORT
    override val localUniqueDataProvider = LocalUniqueDataProvider<ESport>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun players() = resolve("players")
    fun teams() = resolve("teams")
    fun events() = resolve("events")
    fun leagues() = resolve("leagues")
    fun games() = resolve("games")
}
