package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.INDUSTRY_SEGMENTS] category.
 */
@Suppress("unused")
class IndustrySegments internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<IndustrySegments>(fakerService) {
    override val categoryName = CategoryName.INDUSTRY_SEGMENTS
    override val localUniqueDataProvider = LocalUniqueDataProvider<IndustrySegments>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun industry() = resolve("industry")
    fun superSector() = resolve("super_sector")
    fun sector() = resolve("sector")
    fun subSector() = resolve("sub_sector")
}
