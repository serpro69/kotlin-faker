package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.BIG_BANG_THEORY] category.
 */
@Suppress("unused")
class BigBangTheory internal constructor(fakerService: FakerService) : YamlFakeDataProvider<BigBangTheory>(fakerService) {
    override val yamlCategory = YamlCategory.BIG_BANG_THEORY
    override val localUniqueDataProvider = LocalUniqueDataProvider<BigBangTheory>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun characters() = resolve("characters")
    fun quotes() = resolve("quotes")
}
