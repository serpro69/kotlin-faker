package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.FILE] category.
 */
@Suppress("unused")
class File internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<File>(fakerService) {
    override val categoryName = CategoryName.FILE
    override val uniqueDataProvider = UniqueDataProvider<File>()
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    fun extension() = resolve("extension")
    fun mimeType() = resolve("mime_type")
}
