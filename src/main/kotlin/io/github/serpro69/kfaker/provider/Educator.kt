package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.EDUCATOR] category.
 */
class Educator internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.EDUCATOR

    val name = resolve { fakerService.resolve(Faker, it, "name") }
    val secondary = resolve { fakerService.resolve(Faker, it, "secondry") }
    val tertiaryType = resolve { fakerService.resolve(Faker, it, "tertiary", "type") }
    val tertiaryDegree: (type: String) -> String = { type ->
        resolve { fakerService.resolve(Faker, it, "tertiary", "degree", type) }.invoke()
    }
}
