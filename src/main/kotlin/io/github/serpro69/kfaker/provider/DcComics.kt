package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

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
