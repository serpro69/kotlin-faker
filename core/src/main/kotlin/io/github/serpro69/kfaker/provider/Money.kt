package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.Category
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

class Money internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Money>(fakerService) {
    override val category = Category.ofName("MONEY")
    override val localUniqueDataProvider = LocalUniqueDataProvider<Money>()
    override val unique: Money by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    private val currencySymbol by lazy { CurrencySymbol(fakerService).symbol() }

    @JvmOverloads
    fun amount(
        range: IntRange = 0..Int.MAX_VALUE,
        generateDecimals: Boolean = true,
        thousandsSeparator: String = ",",
        decimalSeparator: String = "."
    ): String {
        val randomAmount = fakerService.randomService.nextInt(range).toString()
            .reversed()
            .chunked(3)
            .joinToString(thousandsSeparator)
            .reversed()

        return if (generateDecimals) {
            val randomDecimal = fakerService.randomService.nextInt(0..99).toString().let {
                if (it.length == 1) "0$it" else it
            }
            "$currencySymbol$randomAmount$decimalSeparator$randomDecimal"
        } else "$currencySymbol$randomAmount"
    }
}
