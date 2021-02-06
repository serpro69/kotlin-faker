package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.CategoryName
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [CategoryName.FINANCE] category.
 */
@Suppress("unused")
class Finance internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Finance>(fakerService) {
    override val categoryName = CategoryName.FINANCE
    override val localUniqueDataProvider = LocalUniqueDataProvider<Finance>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    // TODO create and use extension functions in fakerService, similar to `numerify` and `letterify`
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun creditCard(type: String) = resolve("credit_card", type)

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun vatNumber(countryCode: String) = resolve("vat_number", countryCode)
}
