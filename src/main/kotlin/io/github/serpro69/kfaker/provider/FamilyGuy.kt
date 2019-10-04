package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.FAMILY_GUY] category.
 */
@Suppress("unused")
class FamilyGuy internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<FamilyGuy>(fakerService) {
    override val categoryName = CategoryName.FAMILY_GUY
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    fun character() = resolve("character")
    fun location() = resolve("location")
    fun quote() = resolve("quote")
}
