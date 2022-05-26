package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
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
