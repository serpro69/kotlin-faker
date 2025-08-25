package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/** [FakeDataProvider] implementation for [YamlCategory.FILE] category. */
@Suppress("unused")
class File internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<File>(fakerService) {
    override val yamlCategory = YamlCategory.FILE
    override val localUniqueDataProvider = LocalUniqueDataProvider<File>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    val mimeType by lazy { FileMimeType(fakerService) }

    fun extension() = resolve("extension")
}

@Suppress("unused")
class FileMimeType internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<FileMimeType>(fakerService) {
    override val yamlCategory = YamlCategory.FILE
    override val localUniqueDataProvider = LocalUniqueDataProvider<FileMimeType>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun application() = resolve("mime_type", "application")

    fun audio() = resolve("mime_type", "audio")

    fun image() = resolve("mime_type", "image")

    fun message() = resolve("mime_type", "message")

    fun model() = resolve("mime_type", "model")

    fun multipart() = resolve("mime_type", "multipart")

    fun text() = resolve("mime_type", "text")

    fun video() = resolve("mime_type", "video")
}
