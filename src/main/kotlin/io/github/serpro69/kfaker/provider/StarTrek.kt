package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.STAR_TREK] category.
 */
class StarTrek internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.STAR_TREK

    val character = resolve { fakerService.resolve(Faker, it, "character") }
    val location = resolve { fakerService.resolve(Faker, it, "location") }
    val specie = resolve { fakerService.resolve(Faker, it, "specie") }
    val villain = resolve { fakerService.resolve(Faker, it, "villain") }
}
