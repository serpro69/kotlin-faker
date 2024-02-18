package io.github.serpro69.kfaker.creatures.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.ANCIENT] category.
 */
@Suppress("unused")
class Ancient internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Ancient>(fakerService) {
    override val yamlCategory = YamlCategory.ANCIENT
    override val localUniqueDataProvider = LocalUniqueDataProvider<Ancient>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun god() = resolve("god")
    fun primordial() = resolve("primordial")
    fun titan() = resolve("titan")
    fun hero() = resolve("hero")
}
