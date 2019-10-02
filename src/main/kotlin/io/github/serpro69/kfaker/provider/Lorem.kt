package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.LOREM] category.
 */
class Lorem internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.LOREM

    val words = resolve { fakerService.resolve(it, "words") }
    val supplemental = resolve { fakerService.resolve(it, "supplemental") }

    // currently not supported due to logic for getting raw value for List<List<*>> types
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val multibyte = resolve { fakerService.resolve(it, "multibyte") }

    val punctuation = resolve { fakerService.resolve(it, "punctuation", "") }
}
