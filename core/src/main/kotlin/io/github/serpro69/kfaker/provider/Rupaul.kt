package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.RUPAUL] category.
 */
@Suppress("unused")
class Rupaul internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Rupaul>(fakerService) {
    override val categoryName = CategoryName.RUPAUL
    override val localUniqueDataProvider = LocalUniqueDataProvider<Rupaul>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun queens() = resolve("queens")
    fun quotes() = resolve("quotes")
}
