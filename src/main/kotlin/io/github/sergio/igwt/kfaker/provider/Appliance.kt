package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.CREATURE] category.
 */
class Appliance internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.APPLIANCE

    val brand = resolve { fakerService.resolve(Faker, it, "brand") }
    val equipment = resolve { fakerService.resolve(Faker, it, "equipment") }
}