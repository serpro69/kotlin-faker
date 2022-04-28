package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.ANCIENT] category.
 */
@Suppress("unused")
class Ancient internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Ancient>(fakerService) {
    override val category = YamlCategory.ANCIENT
    override val localUniqueDataProvider = LocalUniqueDataProvider<Ancient>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun god() = resolve("god")
    fun primordial() = resolve("primordial")
    fun titan() = resolve("titan")
    fun hero() = resolve("hero")
}
