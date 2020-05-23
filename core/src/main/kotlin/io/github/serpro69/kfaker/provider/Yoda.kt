package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.YODA] category.
 */
@Suppress("unused")
class Yoda internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Yoda>(fakerService) {
    override val categoryName = CategoryName.YODA
    override val localUniqueDataProvider = LocalUniqueDataProvider<Yoda>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun quotes() = resolve("quotes")
}
