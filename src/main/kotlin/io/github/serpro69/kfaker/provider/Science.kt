package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.SCIENCE] category.
 */
class Science internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.SCIENCE

    val element = resolve { fakerService.resolve(it, "element") }
    val elementSymbol = resolve { fakerService.resolve(it, "element_symbol") }
    val scientist = resolve { fakerService.resolve(it, "scientist") }
}
