package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.COFFEE] category.
 */
@Suppress("unused")
class Coffee internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Coffee>(fakerService) {
    override val categoryName = CategoryName.COFFEE
    override val localUniqueDataProvider = LocalUniqueDataProvider<Coffee>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun country() = resolve("country")
    fun regions(country: String) = resolve("regions", country.toLowerCase())
    fun variety() = resolve("variety")
    fun notes() = resolve("notes")
    fun blendName() = resolve("blend_name")
}
