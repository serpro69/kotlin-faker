package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.DRAGON_BALL] category.
 */
@Suppress("unused")
class DragonBall internal constructor(fakerService: FakerService) : YamlFakeDataProvider<DragonBall>(fakerService) {
    override val yamlCategory = YamlCategory.DRAGON_BALL
    override val localUniqueDataProvider = LocalUniqueDataProvider<DragonBall>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun characters() = resolve("characters")
    fun races() = resolve("races")
    fun planets() = resolve("planets")
}
