package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.MARKDOWN] category.
 */
@Suppress("unused")
class Markdown internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Markdown>(fakerService) {
    override val categoryName = CategoryName.MARKDOWN

    fun headers() = resolve("headers")
    fun emphasis() = resolve("emphasis")
}
