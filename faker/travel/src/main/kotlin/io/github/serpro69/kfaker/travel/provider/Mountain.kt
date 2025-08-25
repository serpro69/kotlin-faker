@file:Suppress("unused")

package io.github.serpro69.kfaker.travel.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/** [FakeDataProvider] implementation for [YamlCategory.MOUNTAIN] category. */
class Mountain internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<Mountain>(fakerService) {
    override val yamlCategory = YamlCategory.MOUNTAIN
    override val localUniqueDataProvider = LocalUniqueDataProvider<Mountain>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun name(): String = resolve("name")

    fun range(): String = resolve("range")
}
