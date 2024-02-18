package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.SOURCE] category.
 */
@Suppress("unused")
class Source internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Source>(fakerService) {
    override val yamlCategory = YamlCategory.SOURCE
    override val localUniqueDataProvider = LocalUniqueDataProvider<Source>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    // TODO: 3/10/2019 not sure how this is used. See original faker ruby gem for insights
}
