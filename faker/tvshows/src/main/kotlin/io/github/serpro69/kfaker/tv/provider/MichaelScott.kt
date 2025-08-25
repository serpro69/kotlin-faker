package io.github.serpro69.kfaker.tv.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/** [FakeDataProvider] implementation for [YamlCategory.MICHAEL_SCOTT] category. */
@Suppress("unused")
class MichaelScott internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<MichaelScott>(fakerService) {
    override val yamlCategory = YamlCategory.MICHAEL_SCOTT
    override val localUniqueDataProvider = LocalUniqueDataProvider<MichaelScott>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun quotes() = resolve("quotes")
}
