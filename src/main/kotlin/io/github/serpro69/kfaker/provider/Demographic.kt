package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.DEMOGRAPHIC] category.
 */
@Suppress("unused")
class Demographic internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.DEMOGRAPHIC

    val race = resolve { fakerService.resolve(it, "race") }
    val sex = resolve { fakerService.resolve(it, "sex") }
    val demonym = resolve { fakerService.resolve(it, "demonym") }
    val educationalAttainment = resolve { fakerService.resolve(it, "educational_attainment") }
    val maritalStatus = resolve { fakerService.resolve(it, "marital_status") }
}
