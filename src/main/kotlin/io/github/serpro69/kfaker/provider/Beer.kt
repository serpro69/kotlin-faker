package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.BEER] category.
 */
@Suppress("unused")
class Beer internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.BEER

    val brand = resolve { fakerService.resolve(it, "brand") }
    val name = resolve { fakerService.resolve(it, "name") }
    val hop = resolve { fakerService.resolve(it, "hop") }
    val yeast = resolve { fakerService.resolve(it, "yeast") }
    val malt = resolve { fakerService.resolve(it, "malt") }
    val style = resolve { fakerService.resolve(it, "style") }
}
