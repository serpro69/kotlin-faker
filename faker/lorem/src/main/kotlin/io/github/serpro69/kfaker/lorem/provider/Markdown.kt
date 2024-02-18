package io.github.serpro69.kfaker.lorem.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.MARKDOWN] category.
 */
@Suppress("unused")
class Markdown internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Markdown>(fakerService) {
    override val yamlCategory = YamlCategory.MARKDOWN
    override val localUniqueDataProvider = LocalUniqueDataProvider<Markdown>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun headers() = resolve("headers")
    fun emphasis() = resolve("emphasis")
}
