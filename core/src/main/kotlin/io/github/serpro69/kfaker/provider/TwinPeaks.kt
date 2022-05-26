package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.TWIN_PEAKS] category.
 */
@Suppress("unused")
class TwinPeaks internal constructor(fakerService: FakerService) : YamlFakeDataProvider<TwinPeaks>(fakerService) {
    override val yamlCategory = YamlCategory.TWIN_PEAKS
    override val localUniqueDataProvider = LocalUniqueDataProvider<TwinPeaks>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun characters() = resolve("characters")
    fun locations() = resolve("locations")
    fun quotes() = resolve("quotes")
}
