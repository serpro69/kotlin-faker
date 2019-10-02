package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.UNIVERSITY] category.
 */
class University internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.UNIVERSITY

    // Prefix and suffix are needed because they are used as expressions, i.e. `#{University.prefix}`
    val prefix = resolve { fakerService.resolve(it, "prefix") }
    val suffix = resolve { fakerService.resolve(it, "suffix") }
    val name = resolve { fakerService.resolve(it, "name") }
}
