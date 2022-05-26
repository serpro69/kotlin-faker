@file:Suppress("unused")

package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.BROOKLYN_NINE_NINE] category.
 */
class BrooklynNineNine internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<BrooklynNineNine>(fakerService) {
    override val yamlCategory = YamlCategory.BROOKLYN_NINE_NINE
    override val localUniqueDataProvider = LocalUniqueDataProvider<BrooklynNineNine>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun characters() = resolve("characters")
    fun quotes() = resolve("quotes")
}

