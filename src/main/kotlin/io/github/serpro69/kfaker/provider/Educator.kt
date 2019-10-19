package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.EDUCATOR] category.
 */
@Suppress("unused")
class Educator internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Educator>(fakerService) {
    override val categoryName = CategoryName.EDUCATOR
    override val localUniqueDataProvider = LocalUniqueDataProvider<Educator>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun name() = resolve("name")
    fun secondary() = resolve("secondary")
    fun tertiaryType() = resolve("tertiary", "type")

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    fun tertiaryDegree(type: String) = resolve("tertiary", "degree", type)
}
