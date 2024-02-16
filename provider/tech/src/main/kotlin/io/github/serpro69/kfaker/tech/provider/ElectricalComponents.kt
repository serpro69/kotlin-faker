package io.github.serpro69.kfaker.tech.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.ELECTRICAL_COMPONENTS] category.
 */
@Suppress("unused")
class ElectricalComponents internal constructor(fakerService: FakerService) : YamlFakeDataProvider<ElectricalComponents>(fakerService) {
    override val yamlCategory = YamlCategory.ELECTRICAL_COMPONENTS
    override val localUniqueDataProvider = LocalUniqueDataProvider<ElectricalComponents>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun active() = resolve("active")
    fun passive() = resolve("passive")
    fun electromechanical() = resolve("electromechanical")
}
