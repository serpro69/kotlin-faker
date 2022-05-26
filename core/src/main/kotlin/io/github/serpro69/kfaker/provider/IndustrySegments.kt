package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.INDUSTRY_SEGMENTS] category.
 */
@Suppress("unused")
class IndustrySegments internal constructor(fakerService: FakerService) : YamlFakeDataProvider<IndustrySegments>(fakerService) {
    override val yamlCategory = YamlCategory.INDUSTRY_SEGMENTS
    override val localUniqueDataProvider = LocalUniqueDataProvider<IndustrySegments>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun industry() = resolve("industry")
    fun superSector() = resolve("super_sector")
    fun sector() = resolve("sector")
    fun subSector() = resolve("sub_sector")
}
