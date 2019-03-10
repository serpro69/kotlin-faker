package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

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
