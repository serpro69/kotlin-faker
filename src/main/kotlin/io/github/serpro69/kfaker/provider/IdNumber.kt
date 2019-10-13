package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.ID_NUMBER] category.
 */
@Suppress("unused")
class IdNumber internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<IdNumber>(fakerService) {
    override val categoryName = CategoryName.ID_NUMBER
    override val uniqueDataProvider = UniqueDataProvider<IdNumber>()
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    fun invalid() = resolve("invalid")
}
