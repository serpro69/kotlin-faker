package io.github.serpro69.kfaker.games.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.COIN] category.
 */
@Suppress("unused")
class Coin internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Coin>(fakerService) {
    override val yamlCategory = YamlCategory.COIN
    override val localUniqueDataProvider = LocalUniqueDataProvider<Coin>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun flip() = resolve("flip")
}
