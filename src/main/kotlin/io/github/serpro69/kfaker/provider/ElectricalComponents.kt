package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.ELECTRICAL_COMPONENTS] category.
 */
class ElectricalComponents internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.ELECTRICAL_COMPONENTS

    val active = resolve { fakerService.resolve(Faker, it, "active") }
    val passive = resolve { fakerService.resolve(Faker, it, "passive") }
    val electromechanical = resolve { fakerService.resolve(Faker, it, "electromechanical") }
}
