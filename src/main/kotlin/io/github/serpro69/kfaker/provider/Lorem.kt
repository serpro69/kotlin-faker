package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.LOREM] category.
 */
class Lorem internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.LOREM

    val words = resolve { fakerService.resolve(Faker, it, "words") }
    val supplemental = resolve { fakerService.resolve(Faker, it, "supplemental") }
    val multibyte = resolve { fakerService.resolve(Faker, it, "multibyte") }
    val punctuation = resolve { fakerService.resolve(Faker, it, "punctuation", "") }
}
