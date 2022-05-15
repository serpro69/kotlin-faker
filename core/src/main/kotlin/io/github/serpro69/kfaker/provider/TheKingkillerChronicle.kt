@file:Suppress("unused")

package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.BOOKS] category.
 */
class TheKingkillerChronicle internal constructor(fakerService: FakerService) :
    AbstractFakeDataProvider<TheKingkillerChronicle>(fakerService) {
    override val category = YamlCategory.BOOKS
    override val localUniqueDataProvider = LocalUniqueDataProvider<TheKingkillerChronicle>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun books(): String = resolve("the_kingkiller_chronicle", "books")
    fun characters(): String = resolve("the_kingkiller_chronicle", "characters")
    fun creatures(): String = resolve("the_kingkiller_chronicle", "creatures")
    fun locations(): String = resolve("the_kingkiller_chronicle", "locations")
}
