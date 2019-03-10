package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.FAMILY_GUY] category.
 */
class FamilyGuy internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.FAMILY_GUY

    val character = resolve { fakerService.resolve(Faker, it, "character") }
    val location = resolve { fakerService.resolve(Faker, it, "location") }
    val quote = resolve { fakerService.resolve(Faker, it, "quote") }
}
