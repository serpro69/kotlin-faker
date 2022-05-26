package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.SOUTH_PARK] category.
 */
@Suppress("unused")
class SouthPark internal constructor(fakerService: FakerService) : YamlFakeDataProvider<SouthPark>(fakerService) {
    override val yamlCategory = YamlCategory.SOUTH_PARK
    override val localUniqueDataProvider = LocalUniqueDataProvider<SouthPark>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun characters() = resolve("characters")
    fun quotes() = resolve("quotes")
}
