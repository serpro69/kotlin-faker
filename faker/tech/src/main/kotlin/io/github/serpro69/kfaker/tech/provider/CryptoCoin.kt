package io.github.serpro69.kfaker.tech.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/** [FakeDataProvider] implementation for [YamlCategory.CRYPTO_COIN] category. */
@Suppress("unused")
class CryptoCoin internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<CryptoCoin>(fakerService) {
    override val yamlCategory = YamlCategory.CRYPTO_COIN
    override val localUniqueDataProvider = LocalUniqueDataProvider<CryptoCoin>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun coin() = resolve("coin")
}
