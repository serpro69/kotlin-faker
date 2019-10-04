package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.CREATURE] category.
 */
@Suppress("unused")
class Cat internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Cat>(fakerService) {
    override val categoryName = CategoryName.CREATURE
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val name = resolve { fakerService.resolve(it, "cat", "name") }
    val breed = resolve { fakerService.resolve(it, "cat", "breed") }
    val registry = resolve { fakerService.resolve(it, "cat", "registry") }
}
