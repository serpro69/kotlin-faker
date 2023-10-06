package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.ARCHER] category.
 */
@Suppress("unused")
class Archer internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Archer>(fakerService) {
    override val yamlCategory = YamlCategory.ARCHER
    override val localUniqueDataProvider = LocalUniqueDataProvider<Archer>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun characters() = resolve("characters")
    fun locations() = resolve("locations")
    fun quotes() = resolve("quotes")
}
