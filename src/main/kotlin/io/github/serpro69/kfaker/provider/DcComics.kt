package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.DC_COMICS] category.
 */
@Suppress("unused")
class DcComics internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<DcComics>(fakerService) {
    override val categoryName = CategoryName.DC_COMICS
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    fun hero() = resolve("hero")
    fun heroine() = resolve("heroine")
    fun villain() = resolve("villain")
    fun name() = resolve("name")
    fun title() = resolve("title")
}
