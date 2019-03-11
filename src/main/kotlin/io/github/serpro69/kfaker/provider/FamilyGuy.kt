package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.FAMILY_GUY] category.
 */
class FamilyGuy internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.FAMILY_GUY

    val character = resolve { fakerService.resolve(Faker, it, "character") }
    val location = resolve { fakerService.resolve(Faker, it, "location") }
    val quote = resolve { fakerService.resolve(Faker, it, "quote") }
}
