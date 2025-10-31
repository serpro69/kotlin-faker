package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/** [FakeDataProvider] implementation for [YamlCategory.CURRENCY] category. */
@Suppress("unused")
class Currency internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<Currency>(fakerService) {
    override val yamlCategory = YamlCategory.CURRENCY
    override val localUniqueDataProvider = LocalUniqueDataProvider<Currency>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun code() = resolve("code")

    fun name() = resolve("name")

    fun symbol() = resolve("symbol")
}
