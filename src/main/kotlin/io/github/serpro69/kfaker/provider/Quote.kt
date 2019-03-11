package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

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
