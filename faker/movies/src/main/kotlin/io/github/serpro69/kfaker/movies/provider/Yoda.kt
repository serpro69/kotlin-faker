package io.github.serpro69.kfaker.movies.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/** [FakeDataProvider] implementation for [YamlCategory.YODA] category. */
@Suppress("unused")
class Yoda internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<Yoda>(fakerService) {
    override val yamlCategory = YamlCategory.YODA
    override val localUniqueDataProvider = LocalUniqueDataProvider<Yoda>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun quotes() = resolve("quotes")
}
