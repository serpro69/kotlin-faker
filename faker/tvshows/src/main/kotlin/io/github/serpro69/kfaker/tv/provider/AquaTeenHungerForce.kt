package io.github.serpro69.kfaker.tv.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.AQUA_TEEN_HUNGER_FORCE] category.
 */
@Suppress("unused")
class AquaTeenHungerForce internal constructor(fakerService: FakerService) : YamlFakeDataProvider<AquaTeenHungerForce>(fakerService) {
    override val yamlCategory = YamlCategory.AQUA_TEEN_HUNGER_FORCE
    override val localUniqueDataProvider = LocalUniqueDataProvider<AquaTeenHungerForce>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun character() = resolve("character")
    fun quote() = resolve("quote")
}
