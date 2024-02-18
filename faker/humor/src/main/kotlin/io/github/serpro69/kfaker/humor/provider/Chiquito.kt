package io.github.serpro69.kfaker.humor.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.CHIQUITO] category.
 */
@Suppress("unused")
class Chiquito internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Chiquito>(fakerService) {
    override val yamlCategory = YamlCategory.CHIQUITO
    override val localUniqueDataProvider = LocalUniqueDataProvider<Chiquito>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun expressions() = resolve("expressions")
    fun terms() = resolve("terms")
    fun sentences() = resolve("sentences")
    fun jokes() = resolve("jokes")
}
