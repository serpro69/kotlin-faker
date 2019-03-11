package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.STRANGER_THINGS] category.
 */
class StrangerThings internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.STRANGER_THINGS

    val character = resolve { fakerService.resolve(Faker, it, "character") }
    val quote = resolve { fakerService.resolve(Faker, it, "quote") }
}
