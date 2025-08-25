@file:Suppress("unused")

package io.github.serpro69.kfaker.books.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/** [FakeDataProvider] implementation for [YamlCategory.BOOKS] category. */
class TheKingkillerChronicle internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<TheKingkillerChronicle>(fakerService) {
    override val yamlCategory = YamlCategory.BOOKS
    override val secondaryCategory: Category = Category.ofName("THE_KINGKILLER_CHRONICLE")
    override val localUniqueDataProvider = LocalUniqueDataProvider<TheKingkillerChronicle>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory, secondaryCategory)
    }

    fun books(): String = resolve(secondaryCategory, "books")

    fun characters(): String = resolve(secondaryCategory, "characters")

    fun creatures(): String = resolve(secondaryCategory, "creatures")

    fun locations(): String = resolve(secondaryCategory, "locations")
}
