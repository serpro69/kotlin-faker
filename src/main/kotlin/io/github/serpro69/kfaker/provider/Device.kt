package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.DEVICE] category.
 */
class Device internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.DEVICE

    val modelName = resolve { fakerService.resolve(Faker, it, "model_name") }
    val platform = resolve { fakerService.resolve(Faker, it, "platform") }
    val manufacturer = resolve { fakerService.resolve(Faker, it, "manufacturer") }
    val serial = resolve { fakerService.resolve(Faker, it, "serial") }
}
