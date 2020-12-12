package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [CategoryName.CODE] category.
 */
@Suppress("unused")
class Code internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Code>(fakerService) {
    override val categoryName = CategoryName.CODE
    override val localUniqueDataProvider = LocalUniqueDataProvider<Code>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun asin() = resolve("asin")
}
