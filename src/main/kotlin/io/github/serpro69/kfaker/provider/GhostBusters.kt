package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.GHOSTBUSTERS] category.
 */
@Suppress("unused")
class GhostBusters internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.GHOSTBUSTERS

    val actors = resolve { fakerService.resolve(it, "actors") }
    val characters = resolve { fakerService.resolve(it, "characters") }
    val quotes = resolve { fakerService.resolve(it, "quotes") }
}
