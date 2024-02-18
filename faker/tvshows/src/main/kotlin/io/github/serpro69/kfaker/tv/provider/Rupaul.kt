package io.github.serpro69.kfaker.tv.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.RUPAUL] category.
 */
@Suppress("unused")
class Rupaul internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Rupaul>(fakerService) {
    override val yamlCategory = YamlCategory.RUPAUL
    override val localUniqueDataProvider = LocalUniqueDataProvider<Rupaul>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun queens() = resolve("queens")
    fun quotes() = resolve("quotes")
}
