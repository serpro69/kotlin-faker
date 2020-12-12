package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [CategoryName.BARCODE] category.
 */
@Suppress("unused")
class Barcode internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Barcode>(fakerService) {
    override val categoryName = CategoryName.BARCODE
    override val localUniqueDataProvider = LocalUniqueDataProvider<Barcode>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun ean8() = resolve("ean_8")
    fun ean13() = resolve("ean_13")
    fun upcA() = resolve("upc_a")
    fun upcE() = resolve("upc_e")
    fun compositeSymbol() = resolve("composite_symbol")
    fun isbn() = resolve("isbn")
    fun ismn() = resolve("ismn")
    fun issn() = resolve("issn")
}