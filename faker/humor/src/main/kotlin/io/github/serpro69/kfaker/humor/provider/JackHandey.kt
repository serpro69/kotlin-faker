@file:Suppress("unused")

package io.github.serpro69.kfaker.humor.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.Category
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.QUOTE] category.
 */
class JackHandey internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<JackHandey>(fakerService) {
    override val yamlCategory = YamlCategory.QUOTE
    override val secondaryCategory: Category = Category.ofName("JACK_HANDEY")
    override val localUniqueDataProvider = LocalUniqueDataProvider<JackHandey>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory, secondaryCategory)
    }

    fun quote(): String = resolve("jack_handey")
}
