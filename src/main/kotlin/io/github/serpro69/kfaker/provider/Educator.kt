package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.EDUCATOR] category.
 */
@Suppress("unused")
class Educator internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Educator>(fakerService) {
    override val categoryName = CategoryName.EDUCATOR
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val name = resolve("name")
    val secondary = resolve("secondary")
    val tertiaryType = resolve { fakerService.resolve(it, "tertiary", "type") }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val tertiaryDegree: (type: String) -> String = { type ->
        resolve { fakerService.resolve(it, "tertiary", "degree", type) }.invoke()
    }
}
