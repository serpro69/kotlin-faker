package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.INDUSTRY_SEGMENTS] category.
 */
@Suppress("unused")
class IndustrySegments internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<IndustrySegments>(fakerService) {
    override val categoryName = CategoryName.INDUSTRY_SEGMENTS
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val industry = resolve("industry")
    val superSector = resolve("super_sector")
    val sector = resolve("sector")
    val subSector = resolve("sub_sector")
}
