package io.github.serpro69.kfaker.commerce.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.DESSERT] category.
 */
@Suppress("unused")
class Dessert internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Dessert>(fakerService) {
    override val yamlCategory = YamlCategory.DESSERT
    override val localUniqueDataProvider = LocalUniqueDataProvider<Dessert>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun variety() = resolve("variety")
    fun topping() = resolve("topping")
    fun flavor() = resolve("flavor")
    fun dessert() = { "${flavor()} ${variety()} with ${topping()}" }
}
