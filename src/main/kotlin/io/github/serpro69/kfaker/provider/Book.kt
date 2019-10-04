package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.BOOK] category.
 */
@Suppress("unused")
class Book internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Book>(fakerService) {
    override val categoryName = CategoryName.BOOK
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val title = resolve("title")
    val author = resolve("author")
    val publisher = resolve("publisher")
    val genre = resolve("genre")
}
