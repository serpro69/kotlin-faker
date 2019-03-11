package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.ELECTRICAL_COMPONENTS] category.
 */
class ElectricalComponents internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.ELECTRICAL_COMPONENTS

    val active = resolve { fakerService.resolve(Faker, it, "active") }
    val passive = resolve { fakerService.resolve(Faker, it, "passive") }
    val electromechanical = resolve { fakerService.resolve(Faker, it, "electromechanical") }
}
