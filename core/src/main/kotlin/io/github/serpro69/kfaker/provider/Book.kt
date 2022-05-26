package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.BOOK] category.
 */
@Suppress("unused")
class Book internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Book>(fakerService) {
    override val yamlCategory = YamlCategory.BOOK
    override val localUniqueDataProvider = LocalUniqueDataProvider<Book>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun title() = resolve("title")
    fun author() = resolve("author")
    fun publisher() = resolve("publisher")
    fun genre() = resolve("genre")
}
