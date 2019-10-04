package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.SILICON_VALLEY] category.
 */
@Suppress("unused")
class SiliconValley internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<SiliconValley>(fakerService) {
    override val categoryName = CategoryName.SILICON_VALLEY
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val characters = resolve("characters")
    val companies = resolve("companies")
    val quotes = resolve("quotes")
    val apps = resolve("apps")
    val inventions = resolve("inventions")
    val mottos = resolve("mottos")
    val urls = resolve("urls")
    val email = resolve("email")
}
