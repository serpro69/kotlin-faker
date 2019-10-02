package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.HACKER] category.
 */
@Suppress("unused")
class Hacker internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.HACKER

    val abbreviation = resolve { fakerService.resolve(it, "abbreviation") }
    val adjective = resolve { fakerService.resolve(it, "adjective") }
    val noun = resolve { fakerService.resolve(it, "noun") }
    val verb = resolve { fakerService.resolve(it, "verb") }
    val ingverb = resolve { fakerService.resolve(it, "ingverb") }
}
