package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.BOJACK_HORSEMAN] category.
 */
class BojackHoreseman internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.BOJACK_HORSEMAN

    val characters = resolve { fakerService.resolve(Faker, it, "characters") }
    val quotes = resolve { fakerService.resolve(Faker, it, "quotes") }
    val tongueTwisters = resolve { fakerService.resolve(Faker, it, "tongue_twisters") }
}
