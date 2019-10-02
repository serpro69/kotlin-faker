package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.COFFEE] category.
 */
@Suppress("unused")
class Coffee internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.COFFEE

    val country = resolve { fakerService.resolve(it, "country") }
    val regions: (country: String) -> String = { country ->
        resolve { fakerService.resolve(it, "regions", country.toLowerCase()) }.invoke()
    }
    val variety = resolve { fakerService.resolve(it, "variety") }
    val notes = resolve { fakerService.resolve(it, "notes") }
    val blendName = resolve { fakerService.resolve(it, "blend_name") }
}
