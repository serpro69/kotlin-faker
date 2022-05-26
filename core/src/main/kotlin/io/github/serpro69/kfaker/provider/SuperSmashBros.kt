package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.GAMES] category.
 */
@Suppress("unused")
class SuperSmashBros internal constructor(fakerService: FakerService) : YamlFakeDataProvider<SuperSmashBros>(fakerService) {
    override val yamlCategory = YamlCategory.GAMES
    override val localUniqueDataProvider = LocalUniqueDataProvider<SuperSmashBros>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun fighter() = resolve("super_smash_bros", "fighter")
    fun stage() = resolve("super_smash_bros", "stage")
}
