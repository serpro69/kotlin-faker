package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.BACK_TO_THE_FUTURE] category.
 */
@Suppress("unused")
class BackToTheFuture internal constructor(fakerService: FakerService) : YamlFakeDataProvider<BackToTheFuture>(fakerService) {
    override val yamlCategory = YamlCategory.BACK_TO_THE_FUTURE
    override val localUniqueDataProvider = LocalUniqueDataProvider<BackToTheFuture>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun characters() = resolve("characters")
    fun dates() = resolve("dates")
    fun quotes() = resolve("quotes")
}
