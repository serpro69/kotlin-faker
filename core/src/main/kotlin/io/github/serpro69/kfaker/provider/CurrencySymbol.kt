package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [CategoryName.CURRENCY_SYMBOL] category.
 */
@Suppress("unused")
class CurrencySymbol internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<CurrencySymbol>(fakerService) {
    override val categoryName = CategoryName.CURRENCY_SYMBOL
    override val localUniqueDataProvider = LocalUniqueDataProvider<CurrencySymbol>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun symbol() = resolve("currency_symbol")
}
