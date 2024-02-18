package io.github.serpro69.kfaker.commerce.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.MARKETING] category.
 */
@Suppress("unused")
class Marketing internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Marketing>(fakerService) {
    override val yamlCategory = YamlCategory.MARKETING
    override val localUniqueDataProvider = LocalUniqueDataProvider<Marketing>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun buzzwords() = resolve("buzzwords")
}
