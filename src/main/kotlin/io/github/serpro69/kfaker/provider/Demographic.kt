package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.DEMOGRAPHIC] category.
 */
class Demographic internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.DEMOGRAPHIC

    val race = resolve { fakerService.resolve(Faker, it, "race") }
    val sex = resolve { fakerService.resolve(Faker, it, "sex") }
    val demonym = resolve { fakerService.resolve(Faker, it, "demonym") }
    val educationalAttainment = resolve { fakerService.resolve(Faker, it, "educational_attainment") }
    val maritalStatus = resolve { fakerService.resolve(Faker, it, "marital_status") }
}
