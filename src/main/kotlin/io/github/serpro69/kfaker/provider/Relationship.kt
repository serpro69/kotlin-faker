package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.RELATIONSHIP] category.
 */
class Relationship internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.RELATIONSHIP

    val familialDirect = resolve { fakerService.resolve(Faker, it, "familial", "direct") }
    val familialExtended = resolve { fakerService.resolve(Faker, it, "familial", "extended") }
    val inLaw = resolve { fakerService.resolve(Faker, it, "in_law") }
    val spouse = resolve { fakerService.resolve(Faker, it, "spouse") }
    val parent = resolve { fakerService.resolve(Faker, it, "parent") }
    val sibling = resolve { fakerService.resolve(Faker, it, "sibling") }
}
