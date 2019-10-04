package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.LOREM] category.
 */
@Suppress("unused")
class Lorem internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Lorem>(fakerService) {
    override val categoryName = CategoryName.LOREM
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val words = resolve("words")
    val supplemental = resolve("supplemental")

    // currently not supported due to logic for getting raw value for List<List<*>> types
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val multibyte = resolve("multibyte")

    val punctuation = resolve { fakerService.resolve(it, "punctuation", "") }
}
