package io.github.serpro69.kfaker.movies.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/** [FakeDataProvider] implementation for [YamlCategory.ANCIENT] category. */
@Suppress("unused")
class Rajnikanth internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<Rajnikanth>(fakerService) {
    override val yamlCategory = YamlCategory.RAJNIKANTH
    override val localUniqueDataProvider = LocalUniqueDataProvider<Rajnikanth>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun joke() = resolve("joke")
}
