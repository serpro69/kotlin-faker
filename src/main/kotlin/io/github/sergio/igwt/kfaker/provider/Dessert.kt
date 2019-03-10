package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.DESSERT] category.
 */
class Dessert internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.DESSERT

    val variety = resolve { fakerService.resolve(Faker, it, "variety") }
    val topping = resolve { fakerService.resolve(Faker, it, "topping") }
    val flavor = resolve { fakerService.resolve(Faker, it, "flavor") }
}
