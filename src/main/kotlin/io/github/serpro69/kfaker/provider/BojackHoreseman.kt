package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.BOJACK_HORSEMAN] category.
 */
@Suppress("unused")
class BojackHoreseman internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.BOJACK_HORSEMAN

    val characters = resolve { fakerService.resolve(it, "characters") }
    val quotes = resolve { fakerService.resolve(it, "quotes") }
    val tongueTwisters = resolve { fakerService.resolve(it, "tongue_twisters") }
}
