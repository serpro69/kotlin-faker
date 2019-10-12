package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.NEW_GIRL] category.
 */
@Suppress("unused")
class NewGirl internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<NewGirl>(fakerService) {
    override val categoryName = CategoryName.NEW_GIRL

    fun characters() = resolve("characters")
    fun quotes() = resolve("quotes")
}
