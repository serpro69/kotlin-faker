package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.SPONGEBOB] category.
 */
@Suppress("unused")
class Spongebob internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Spongebob>(fakerService) {
    override val yamlCategory = YamlCategory.SPONGEBOB
    override val localUniqueDataProvider = LocalUniqueDataProvider<Spongebob>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun characters() = resolve("characters")
    fun quotes() = resolve("quotes")
    fun episodes() = resolve("episodes")
}
