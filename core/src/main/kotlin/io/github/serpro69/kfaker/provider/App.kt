package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.APP] category.
 */
@Suppress("unused")
class App internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<App>(fakerService) {
    override val category = YamlCategory.APP
    override val localUniqueDataProvider = LocalUniqueDataProvider<App>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun name() = resolve("name")
    fun version() = with(fakerService) { resolve("version").numerify() }
    fun author() = resolve("author")
}
