package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.SILICON_VALLEY] category.
 */
@Suppress("unused")
class SiliconValley internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<SiliconValley>(fakerService) {
    override val categoryName = CategoryName.SILICON_VALLEY
    override val localUniqueDataProvider = LocalUniqueDataProvider<SiliconValley>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun characters() = resolve("characters")
    fun companies() = resolve("companies")
    fun quotes() = resolve("quotes")
    fun apps() = resolve("apps")
    fun inventions() = resolve("inventions")
    fun mottos() = resolve("mottos")
    fun urls() = resolve("urls")
    fun email() = resolve("email")
}
