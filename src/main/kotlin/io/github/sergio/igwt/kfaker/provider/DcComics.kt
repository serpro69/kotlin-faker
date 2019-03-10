package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.DC_COMICS] category.
 */
class DcComics internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.DC_COMICS

    val hero = resolve { fakerService.resolve(Faker, it, "hero") }
    val heroine = resolve { fakerService.resolve(Faker, it, "heroine") }
    val villain = resolve { fakerService.resolve(Faker, it, "villain") }
    val name = resolve { fakerService.resolve(Faker, it, "name") }
    val title = resolve { fakerService.resolve(Faker, it, "title") }
}
