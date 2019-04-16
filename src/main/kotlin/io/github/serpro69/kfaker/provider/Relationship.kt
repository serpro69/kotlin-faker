package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.RELATIONSHIP] category.
 */
class Relationship internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.RELATIONSHIP

    val familialDirect = resolve { fakerService.resolve(Faker, it, "familial", "direct") }
    val familialExtended = resolve { fakerService.resolve(Faker, it, "familial", "extended") }
    val familial = resolve { fakerService.resolve(Faker, it, "familial", "") }
    val inLaw = resolve { fakerService.resolve(Faker, it, "in_law") }
    val spouse = resolve { fakerService.resolve(Faker, it, "spouse") }
    val parent = resolve { fakerService.resolve(Faker, it, "parent") }
    val sibling = resolve { fakerService.resolve(Faker, it, "sibling") }
}
