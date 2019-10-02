package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.THE_IT_CROWD] category.
 */
class TheITCrowd internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.THE_IT_CROWD

    val actors = resolve { fakerService.resolve(it, "actors") }
    val characters = resolve { fakerService.resolve(it, "characters") }
    val emails = resolve { fakerService.resolve(it, "emails") }
    val quotes = resolve { fakerService.resolve(it, "quotes") }
}
