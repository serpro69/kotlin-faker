package io.github.serpro69.kfaker.tv.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.THE_THICK_OF_IT] category.
 */
@Suppress("unused")
class TheThickOfIt internal constructor(fakerService: FakerService) : YamlFakeDataProvider<TheThickOfIt>(fakerService) {
    override val yamlCategory = YamlCategory.THE_THICK_OF_IT
    override val localUniqueDataProvider = LocalUniqueDataProvider<TheThickOfIt>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun characters() = resolve("characters")
    fun positions() = resolve("positions")
    fun departments() = resolve("departments")
}
