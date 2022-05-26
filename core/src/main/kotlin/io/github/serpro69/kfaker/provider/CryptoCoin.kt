package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.CRYPTO_COIN] category.
 */
@Suppress("unused")
class CryptoCoin internal constructor(fakerService: FakerService) : YamlFakeDataProvider<CryptoCoin>(fakerService) {
    override val yamlCategory = YamlCategory.CRYPTO_COIN
    override val localUniqueDataProvider = LocalUniqueDataProvider<CryptoCoin>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun coin() = resolve("coin")
}
