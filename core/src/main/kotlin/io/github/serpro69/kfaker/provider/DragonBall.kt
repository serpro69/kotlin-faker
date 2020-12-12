package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [CategoryName.DRAGON_BALL] category.
 */
@Suppress("unused")
class DragonBall internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<DragonBall>(fakerService) {
    override val categoryName = CategoryName.DRAGON_BALL
    override val localUniqueDataProvider = LocalUniqueDataProvider<DragonBall>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun characters() = resolve("characters")
}
