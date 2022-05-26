package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.BUSINESS] category.
 */
@Suppress("unused")
class Business internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Business>(fakerService) {
    override val yamlCategory = YamlCategory.BUSINESS
    override val localUniqueDataProvider = LocalUniqueDataProvider<Business>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun creditCardNumbers() = resolve("credit_card_numbers")
    fun creditCardTypes() = resolve("credit_card_types")
}
