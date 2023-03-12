package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.CHIQUITO] category.
 */
@Suppress("unused")
class Chiquito internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Chiquito>(fakerService) {
    override val yamlCategory = YamlCategory.CHIQUITO
    override val localUniqueDataProvider = LocalUniqueDataProvider<Chiquito>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun expressions() = resolve("expressions")
    fun terms() = resolve("terms")
    fun sentences() = resolve("sentences")
    fun jokes() = resolve("jokes")
}
