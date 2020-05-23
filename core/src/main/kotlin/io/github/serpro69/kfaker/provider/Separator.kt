package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.ADDRESS] category.
 */
@Suppress("unused")
class Separator internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Separator>(fakerService) {
    override val categoryName = CategoryName.SEPARATOR
    override val localUniqueDataProvider = LocalUniqueDataProvider<Separator>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun separator() = resolve("separator")
}
