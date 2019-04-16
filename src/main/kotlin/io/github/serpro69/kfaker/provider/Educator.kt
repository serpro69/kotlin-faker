package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.EDUCATOR] category.
 */
class Educator internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.EDUCATOR

    val name = resolve { fakerService.resolve(Faker, it, "name") }
    val secondary = resolve { fakerService.resolve(Faker, it, "secondary") }
    val tertiaryType = resolve { fakerService.resolve(Faker, it, "tertiary", "type") }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Not fully implemented")
    val tertiaryDegree: (type: String) -> String = { type ->
        resolve { fakerService.resolve(Faker, it, "tertiary", "degree", type) }.invoke()
    }
}
