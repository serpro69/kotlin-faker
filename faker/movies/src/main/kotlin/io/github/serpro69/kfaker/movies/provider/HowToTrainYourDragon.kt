@file:Suppress("unused")

package io.github.serpro69.kfaker.movies.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.HOW_TO_TRAIN_YOUR_DRAGON] category.
 */
class HowToTrainYourDragon internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<HowToTrainYourDragon>(fakerService) {
    override val yamlCategory = YamlCategory.HOW_TO_TRAIN_YOUR_DRAGON
    override val localUniqueDataProvider = LocalUniqueDataProvider<HowToTrainYourDragon>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun characters(): String = resolve("characters")
    fun dragons(): String = resolve("dragons")
    fun locations(): String = resolve("locations")
}
