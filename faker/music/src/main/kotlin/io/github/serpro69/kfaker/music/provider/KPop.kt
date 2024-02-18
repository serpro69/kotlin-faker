package io.github.serpro69.kfaker.music.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.KPOP] category.
 */
@Suppress("unused")
class KPop internal constructor(fakerService: FakerService) : YamlFakeDataProvider<KPop>(fakerService) {
    override val yamlCategory = YamlCategory.KPOP
    override val localUniqueDataProvider = LocalUniqueDataProvider<KPop>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun firstGroups() = resolve("i_groups")
    fun secondGroups() = resolve("ii_groups")
    fun thirdGroups() = resolve("iii_groups")
    fun girlGroups() = resolve("girl_groups")
    fun boyBands() = resolve("boy_bands")
    fun solo() = resolve("solo")
}
