package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.BASKETBALL] category.
 */
@Suppress("unused")
class Basketball internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Basketball>(fakerService) {
    override val categoryName = CategoryName.BASKETBALL
    override val localUniqueDataProvider = LocalUniqueDataProvider<Basketball>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun teams() = resolve("teams")
    fun players() = resolve("players")
    fun coaches() = resolve("coaches")
    fun positions() = resolve("positions")
}
