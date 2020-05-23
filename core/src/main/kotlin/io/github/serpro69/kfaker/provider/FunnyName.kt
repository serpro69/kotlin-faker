package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.FUNNY_NAME] category.
 */
@Suppress("unused")
class FunnyName internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<FunnyName>(fakerService) {
    override val categoryName = CategoryName.FUNNY_NAME
    override val localUniqueDataProvider = LocalUniqueDataProvider<FunnyName>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun name() = resolve("name")
}
