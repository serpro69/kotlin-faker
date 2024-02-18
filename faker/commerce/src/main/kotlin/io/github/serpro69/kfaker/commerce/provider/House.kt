package io.github.serpro69.kfaker.commerce.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.HOUSE] category.
 */
@Suppress("unused")
class House internal constructor(fakerService: FakerService) : YamlFakeDataProvider<House>(fakerService) {
    override val yamlCategory = YamlCategory.HOUSE
    override val localUniqueDataProvider = LocalUniqueDataProvider<House>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun furniture() = resolve("furniture")
    fun rooms() = resolve("rooms")
}
