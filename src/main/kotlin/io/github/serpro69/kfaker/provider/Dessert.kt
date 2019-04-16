package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.DESSERT] category.
 */
class Dessert internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.DESSERT

    val variety = resolve { fakerService.resolve(Faker, it, "variety") }
    val topping = resolve { fakerService.resolve(Faker, it, "topping") }
    val flavor = resolve { fakerService.resolve(Faker, it, "flavor") }
    val dessert = { "${flavor()} ${variety()} with ${topping()}" }
}
