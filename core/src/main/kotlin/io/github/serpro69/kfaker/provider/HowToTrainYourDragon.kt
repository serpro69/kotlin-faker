@file:Suppress("unused")

package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.HOW_TO_TRAIN_YOUR_DRAGON] category.
 */
class HowToTrainYourDragon internal constructor(fakerService: FakerService) :
    AbstractFakeDataProvider<HowToTrainYourDragon>(fakerService) {
    override val category = YamlCategory.HOW_TO_TRAIN_YOUR_DRAGON
    override val localUniqueDataProvider = LocalUniqueDataProvider<HowToTrainYourDragon>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun characters(): String = resolve("characters")
    fun dragons(): String = resolve("dragons")
    fun locations(): String = resolve("locations")
}
