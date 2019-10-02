package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.HEROES] category.
 */
@Suppress("unused")
class Heroes internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.HEROES

    val names = resolve { fakerService.resolve(it, "names") }
    val specialties = resolve { fakerService.resolve(it, "specialties") }
    val klasses = resolve { fakerService.resolve(it, "klasses") }
}
