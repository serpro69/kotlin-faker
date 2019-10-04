package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.NATION] category.
 */
@Suppress("unused")
class Nation internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Nation>(fakerService) {
    override val categoryName = CategoryName.NATION
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    // currently not supported due to logic for getting raw value for List<List<*>> types
    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val flag = resolve("flag")

    val nationality = resolve("nationality")
    val language = resolve("language")
    val capitalCity = resolve("capital_city")
}
