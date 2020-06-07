package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.BLOOD] category.
 */
@Suppress("unused")
class Blood internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Blood>(fakerService) {
    override val categoryName = CategoryName.BLOOD
    override val localUniqueDataProvider = LocalUniqueDataProvider<Blood>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun type() = resolve("type")
    fun rhFactor() = resolve("rh_factor")
    fun group() = resolve("group")
}
