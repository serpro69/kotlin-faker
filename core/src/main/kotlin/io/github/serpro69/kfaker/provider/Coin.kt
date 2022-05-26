package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
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
