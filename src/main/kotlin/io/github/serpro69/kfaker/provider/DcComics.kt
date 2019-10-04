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

    val hero = resolve("hero")
    val heroine = resolve("heroine")
    val villain = resolve("villain")
    val name = resolve("name")
    val title = resolve("title")
}
