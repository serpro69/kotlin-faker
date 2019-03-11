package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.HACKER] category.
 */
class Hacker internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.HACKER

    val abbreviation = resolve { fakerService.resolve(Faker, it, "abbreviation") }
    val adjective = resolve { fakerService.resolve(Faker, it, "adjective") }
    val noun = resolve { fakerService.resolve(Faker, it, "noun") }
    val verb = resolve { fakerService.resolve(Faker, it, "verb") }
    val ingverb = resolve { fakerService.resolve(Faker, it, "ingverb") }
}
