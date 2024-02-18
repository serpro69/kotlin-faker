package io.github.serpro69.kfaker.commerce.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.BEER] category.
 */
@Suppress("unused")
class Beer internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Beer>(fakerService) {
    override val yamlCategory = YamlCategory.BEER
    override val localUniqueDataProvider = LocalUniqueDataProvider<Beer>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun brand() = resolve("brand")
    fun name() = resolve("name")
    fun hop() = resolve("hop")
    fun yeast() = resolve("yeast")
    fun malt() = resolve("malt")
    fun style() = resolve("style")
}
