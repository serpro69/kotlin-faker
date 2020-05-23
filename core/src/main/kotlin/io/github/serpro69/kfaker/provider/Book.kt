package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.BOOK] category.
 */
@Suppress("unused")
class Book internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Book>(fakerService) {
    override val categoryName = CategoryName.BOOK
    override val localUniqueDataProvider = LocalUniqueDataProvider<Book>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun title() = resolve("title")
    fun author() = resolve("author")
    fun publisher() = resolve("publisher")
    fun genre() = resolve("genre")
}
