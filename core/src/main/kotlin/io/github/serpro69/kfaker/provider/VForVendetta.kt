package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.V_FOR_VENDETTA] category.
 */
@Suppress("unused")
class VForVendetta internal constructor(fakerService: FakerService) : YamlFakeDataProvider<VForVendetta>(fakerService) {
    override val yamlCategory = YamlCategory.V_FOR_VENDETTA
    override val localUniqueDataProvider = LocalUniqueDataProvider<VForVendetta>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun characters() = resolve("characters")
    fun speeches() = resolve("speeches")
    fun quotes() = resolve("quotes")
}
