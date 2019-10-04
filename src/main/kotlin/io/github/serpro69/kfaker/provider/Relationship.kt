package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.RELATIONSHIP] category.
 */
@Suppress("unused")
class Relationship internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Relationship>(fakerService) {
    override val categoryName = CategoryName.RELATIONSHIP
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val familialDirect = resolve { fakerService.resolve(it, "familial", "direct") }
    val familialExtended = resolve { fakerService.resolve(it, "familial", "extended") }
    val familial = resolve { fakerService.resolve(it, "familial", "") }
    val inLaw = resolve("in_law")
    val spouse = resolve("spouse")
    val parent = resolve("parent")
    val sibling = resolve("sibling")
}
