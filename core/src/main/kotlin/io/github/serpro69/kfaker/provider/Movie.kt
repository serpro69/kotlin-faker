package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.MOVIE] category.
 */
@Suppress("unused")
class Movie internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Movie>(fakerService) {
    override val yamlCategory = YamlCategory.MOVIE
    override val localUniqueDataProvider = LocalUniqueDataProvider<Movie>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun title() = resolve("title")
    fun quote() = resolve("quote")
}
