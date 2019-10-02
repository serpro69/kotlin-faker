package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.QUOTE] category.
 */
@Suppress("unused")
class Quote internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.QUOTE

    val famousLastWords = resolve { fakerService.resolve(it, "famous_last_words") }
    val matz = resolve { fakerService.resolve(it, "matz") }
    val mostInterestingManInTheWorld = resolve { fakerService.resolve(it, "most_interesting_man_in_the_world") }
    val robin = resolve { fakerService.resolve(it, "robin") }
    val singularSiegler = resolve { fakerService.resolve(it, "singular_siegler") }
    val yoda = resolve { fakerService.resolve(it, "yoda") }
}
