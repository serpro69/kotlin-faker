package io.github.serpro69.kfaker.tv.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/** [FakeDataProvider] implementation for [YamlCategory.STARGATE] category. */
@Suppress("unused")
class Stargate internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<Stargate>(fakerService) {
    override val yamlCategory = YamlCategory.STARGATE
    override val localUniqueDataProvider = LocalUniqueDataProvider<Stargate>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun characters() = resolve("characters")

    fun planets() = resolve("planets")

    fun quotes() = resolve("quotes")
}
