package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.THE_IT_CROWD] category.
 */
class TheITCrowd internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.THE_IT_CROWD

    val actors = resolve { fakerService.resolve(Faker, it, "actors") }
    val characters = resolve { fakerService.resolve(Faker, it, "characters") }
    val emails = resolve { fakerService.resolve(Faker, it, "emails") }
    val quotes = resolve { fakerService.resolve(Faker, it, "quotes") }
}
