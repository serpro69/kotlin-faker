package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.RELATIONSHIP] category.
 */
@Suppress("unused")
class Relationship internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Relationship>(fakerService) {
    override val categoryName = CategoryName.RELATIONSHIP
    override val localUniqueDataProvider = LocalUniqueDataProvider<Relationship>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun familialDirect() = resolve("familial", "direct")
    fun familialExtended() = resolve("familial", "extended")
    fun familial() = resolve("familial", "")
    fun inLaw() = resolve("in_law")
    fun spouse() = resolve("spouse")
    fun parent() = resolve("parent")
    fun sibling() = resolve("sibling")
}
