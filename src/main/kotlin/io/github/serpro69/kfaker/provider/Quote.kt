package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.QUOTE] category.
 */
class Quote internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.QUOTE

    val famousLastWords = resolve { fakerService.resolve(Faker, it, "famous_last_words") }
    val matz = resolve { fakerService.resolve(Faker, it, "matz") }
    val mostInterestingManInTheWorld = resolve { fakerService.resolve(Faker, it, "most_interesting_man_in_the_world") }
    val robin = resolve { fakerService.resolve(Faker, it, "robin") }
    val singularSiegler = resolve { fakerService.resolve(Faker, it, "singular_siegler") }
    val yoda = resolve { fakerService.resolve(Faker, it, "yoda") }
}
