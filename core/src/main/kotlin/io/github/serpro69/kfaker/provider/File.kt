package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.FILE] category.
 */
@Suppress("unused")
class File internal constructor(fakerService: FakerService) : YamlFakeDataProvider<File>(fakerService) {
    override val yamlCategory = YamlCategory.FILE
    override val localUniqueDataProvider = LocalUniqueDataProvider<File>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun extension() = resolve("extension")
    fun mimeType() = resolve("mime_type")
}
