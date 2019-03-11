package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.HOUSE] category.
 */
class House internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.HOUSE

    val furniture = resolve { fakerService.resolve(Faker, it, "funiture") }
    val rooms = resolve { fakerService.resolve(Faker, it, "ooms") }
}
