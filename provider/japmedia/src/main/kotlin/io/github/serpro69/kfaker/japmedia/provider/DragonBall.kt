package io.github.serpro69.kfaker.japmedia.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
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
