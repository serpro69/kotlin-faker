@file:Suppress("unused")

package io.github.serpro69.kfaker.travel.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.AUSTRALIA] category.
 */
class Australia internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Australia>(fakerService) {
    override val yamlCategory = YamlCategory.AUSTRALIA
    override val localUniqueDataProvider = LocalUniqueDataProvider<Australia>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun locations() = resolve("locations")
    fun animals() = resolve("animals")
    fun states() = resolve("states")
}

