package io.github.serpro69.kfaker.tv.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/** [FakeDataProvider] implementation for [YamlCategory.SUITS] category. */
@Suppress("unused")
class Suits internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<Suits>(fakerService) {
    override val yamlCategory = YamlCategory.SUITS
    override val localUniqueDataProvider = LocalUniqueDataProvider<Suits>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun characters() = resolve("characters")

    fun quotes() = resolve("quotes")
}
