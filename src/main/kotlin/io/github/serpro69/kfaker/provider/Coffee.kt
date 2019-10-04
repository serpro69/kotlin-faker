package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.COFFEE] category.
 */
@Suppress("unused")
class Coffee internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Coffee>(fakerService) {
    override val categoryName = CategoryName.COFFEE
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val country = resolve("country")
    val regions: (country: String) -> String = { country ->
        resolve { fakerService.resolve(it, "regions", country.toLowerCase()) }.invoke()
    }
    val variety = resolve("variety")
    val notes = resolve("notes")
    val blendName = resolve("blend_name")
}
