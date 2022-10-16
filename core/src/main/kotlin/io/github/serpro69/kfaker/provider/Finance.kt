package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.FINANCE] category.
 */
@Suppress("unused")
class Finance internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Finance>(fakerService) {
    override val yamlCategory = YamlCategory.FINANCE
    override val localUniqueDataProvider = LocalUniqueDataProvider<Finance>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun creditCard(type: String): String = with(fakerService) { resolve("credit_card", type).numerify().generexify() }
    fun condominiumFiscalCode(): String = with(fakerService) { resolve("condominium_fiscal_code", "IT").numerify() }
    fun vatNumber(countryCode: String): String = with(fakerService) { resolve("vat_number", countryCode).numerify() }
    fun ticker(stockExchange: StockExchange = fakerService.randomService.nextEnum()): String =
        resolve("ticker", stockExchange.name.lowercase())

    fun stockMarket(): String = resolve("stock_market")
}

enum class StockExchange {
    NASDAQ,
    NYSE,
    ;
}
