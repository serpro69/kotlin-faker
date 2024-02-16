@file:Suppress("unused")

package io.github.serpro69.kfaker.tv.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.THE_OFFICE] category.
 */
class TheOffice internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<TheOffice>(fakerService) {
    override val yamlCategory = YamlCategory.THE_OFFICE
    override val localUniqueDataProvider = LocalUniqueDataProvider<TheOffice>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun characters(): String = resolve("characters")
    fun quotes(): String = resolve("quotes")
}
