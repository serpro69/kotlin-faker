package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

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
