package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.GENDER] category.
 */
@Suppress("unused")
class Gender internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Gender>(fakerService) {
    override val categoryName = CategoryName.GENDER
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    fun types() = resolve("types")
    fun binaryTypes() = resolve("binary_types")
}
