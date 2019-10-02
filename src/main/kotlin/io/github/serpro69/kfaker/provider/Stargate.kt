package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.STARGATE] category.
 */
@Suppress("unused")
class Stargate internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.STARGATE

    val characters = resolve { fakerService.resolve(it, "characters") }
    val planets = resolve { fakerService.resolve(it, "planets") }
    val quotes = resolve { fakerService.resolve(it, "quotes") }
}
