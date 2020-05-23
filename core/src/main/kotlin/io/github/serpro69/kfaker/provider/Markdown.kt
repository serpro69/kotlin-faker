package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.MARKDOWN] category.
 */
@Suppress("unused")
class Markdown internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Markdown>(fakerService) {
    override val categoryName = CategoryName.MARKDOWN
    override val localUniqueDataProvider = LocalUniqueDataProvider<Markdown>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun headers() = resolve("headers")
    fun emphasis() = resolve("emphasis")
}
