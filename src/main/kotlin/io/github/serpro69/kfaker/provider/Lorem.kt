package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

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
