package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.CREATURE] category.
 */
class Cat internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.CREATURE

    val name = resolve { fakerService.resolve(it, "cat", "name") }
    val breed = resolve { fakerService.resolve(it, "cat", "breed") }
    val registry = resolve { fakerService.resolve(it, "cat", "registry") }
}
