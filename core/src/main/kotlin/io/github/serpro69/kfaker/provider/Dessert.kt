package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.DESSERT] category.
 */
@Suppress("unused")
class Dessert internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Dessert>(fakerService) {
    override val categoryName = CategoryName.DESSERT
    override val localUniqueDataProvider = LocalUniqueDataProvider<Dessert>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun variety() = resolve("variety")
    fun topping() = resolve("topping")
    fun flavor() = resolve("flavor")
    fun dessert() = { "${flavor()} ${variety()} with ${topping()}" }
}
