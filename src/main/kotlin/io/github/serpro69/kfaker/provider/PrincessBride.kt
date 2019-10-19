package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.PRINCESS_BRIDE] category.
 */
@Suppress("unused")
class PrincessBride internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<PrincessBride>(fakerService) {
    override val categoryName = CategoryName.PRINCESS_BRIDE
    override val localUniqueDataProvider = LocalUniqueDataProvider<PrincessBride>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun characters() = resolve("characters")
    fun quotes() = resolve("quotes")
}
