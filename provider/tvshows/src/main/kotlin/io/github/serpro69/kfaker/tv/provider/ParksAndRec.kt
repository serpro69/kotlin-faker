package io.github.serpro69.kfaker.tv.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.PARKS_AND_REC] category.
 */
@Suppress("unused")
class ParksAndRec internal constructor(fakerService: FakerService) : YamlFakeDataProvider<ParksAndRec>(fakerService) {
    override val yamlCategory = YamlCategory.PARKS_AND_REC
    override val localUniqueDataProvider = LocalUniqueDataProvider<ParksAndRec>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun characters() = resolve("characters")
    fun cities() = resolve("cities")
}
