package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.DESSERT] category.
 */
@Suppress("unused")
class Dessert internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.DESSERT

    val variety = resolve { fakerService.resolve(it, "variety") }
    val topping = resolve { fakerService.resolve(it, "topping") }
    val flavor = resolve { fakerService.resolve(it, "flavor") }
    val dessert = { "${flavor()} ${variety()} with ${topping()}" }
}
