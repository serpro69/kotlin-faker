package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.CREATURE] category.
 */
class Coffee internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.COFFEE

    val country = resolve { fakerService.resolve(Faker, it, "country") }
    val regions: (region: String) -> String = { region ->
        resolve { fakerService.resolve(Faker, it, "regions", region) }.invoke()
    }
    val variety = resolve { fakerService.resolve(Faker, it, "variety") }
    val notes = resolve { fakerService.resolve(Faker, it, "notes") }
    val blendName = resolve { fakerService.resolve(Faker, it, "blend_name") }
}
