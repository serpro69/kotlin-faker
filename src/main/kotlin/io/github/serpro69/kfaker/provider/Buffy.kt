package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.BUFFY] category.
 */
class Buffy internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.BUFFY

    val characters = resolve { fakerService.resolve(Faker, it, "characters") }
    val quotes = resolve { fakerService.resolve(Faker, it, "quotes") }
    val celebrities = resolve { fakerService.resolve(Faker, it, "celebrities") }
    val bigBads = resolve { fakerService.resolve(Faker, it, "big_bads") }
    val episodes = resolve { fakerService.resolve(Faker, it, "episodes") }
}
