package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.APPLIANCE] category.
 */
@Suppress("unused")
class Appliance internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.APPLIANCE

    val brand = resolve { fakerService.resolve(it, "brand") }
    val equipment = resolve { fakerService.resolve(it, "equipment") }
}