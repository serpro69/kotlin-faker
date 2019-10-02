package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.ANCIENT] category.
 */
class Ancient internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.ANCIENT

    val god = resolve { fakerService.resolve(it, "god") }
    val primordial = resolve { fakerService.resolve(it, "primordial") }
    val titan = resolve { fakerService.resolve(it, "titan") }
    val hero = resolve { fakerService.resolve(it, "hero") }
}
