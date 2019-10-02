package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.CHUCK_NORRIS] category.
 */
class ChuckNorris internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.CHUCK_NORRIS

    val fact = resolve { fakerService.resolve(it, "fact") }
}
