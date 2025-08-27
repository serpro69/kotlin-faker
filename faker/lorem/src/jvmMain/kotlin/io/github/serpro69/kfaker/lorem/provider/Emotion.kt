package io.github.serpro69.kfaker.lorem.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/** [FakeDataProvider] implementation for [YamlCategory.EMOTION] category. */
@Suppress("unused")
class Emotion internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<Emotion>(fakerService) {
    override val yamlCategory = YamlCategory.EMOTION
    override val localUniqueDataProvider = LocalUniqueDataProvider<Emotion>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun adjective(): String = resolve("adjective")

    fun noun(): String = resolve("noun")
}
