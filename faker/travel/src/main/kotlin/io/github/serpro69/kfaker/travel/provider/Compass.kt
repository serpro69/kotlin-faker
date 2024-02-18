package io.github.serpro69.kfaker.travel.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.COMPASS] category.
 */
@Suppress("unused")
class Compass internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Compass>(fakerService) {
    override val yamlCategory = YamlCategory.COMPASS
    override val localUniqueDataProvider = LocalUniqueDataProvider<Compass>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    // TODO: 3/7/2019 this needs some custom logic for the resolver function
}
