@file:Suppress("unused")

package io.github.serpro69.kfaker.tv.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.FINAL_SPACE] category.
 */
class FinalSpace internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<FinalSpace>(fakerService) {
    override val yamlCategory = YamlCategory.FINAL_SPACE
    override val localUniqueDataProvider = LocalUniqueDataProvider<FinalSpace>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun characters(): String = resolve("characters")
    fun vehicles(): String = resolve("vehicles")
    fun quotes(): String = resolve("quotes")
}

