package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.BUFFY] category.
 */
@Suppress("unused")
class Buffy internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.BUFFY

    val characters = resolve { fakerService.resolve(it, "characters") }
    val quotes = resolve { fakerService.resolve(it, "quotes") }
    val celebrities = resolve { fakerService.resolve(it, "celebrities") }
    val bigBads = resolve { fakerService.resolve(it, "big_bads") }
    val episodes = resolve { fakerService.resolve(it, "episodes") }
}
