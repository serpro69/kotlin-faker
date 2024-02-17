package io.github.serpro69.kfaker.lorem.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.QUOTE] category.
 */
@Suppress("unused")
class Quote internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Quote>(fakerService) {
    override val yamlCategory = YamlCategory.QUOTE
    override val localUniqueDataProvider = LocalUniqueDataProvider<Quote>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun famousLastWords() = resolve("famous_last_words")
    fun matz() = resolve("matz")
    fun mostInterestingManInTheWorld() = resolve("most_interesting_man_in_the_world")
    fun robin() = resolve("robin")
    fun singularSiegler() = resolve("singular_siegler")
    fun yoda() = resolve("yoda")
    fun fortuneCookie() = resolve("fortune_cookie")
}
