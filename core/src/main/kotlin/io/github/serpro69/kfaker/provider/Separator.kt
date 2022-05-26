package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.ADDRESS] category.
 */
@Suppress("unused")
class Separator internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Separator>(fakerService) {
    override val yamlCategory = YamlCategory.SEPARATOR
    override val localUniqueDataProvider = LocalUniqueDataProvider<Separator>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun separator() = resolve("separator")
}
