package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.BLOOD] category.
 */
@Suppress("unused")
class Blood internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Blood>(fakerService) {
    override val yamlCategory = YamlCategory.BLOOD
    override val localUniqueDataProvider = LocalUniqueDataProvider<Blood>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun type() = resolve("type")
    fun rhFactor() = resolve("rh_factor")
    fun group() = resolve("group")
}
