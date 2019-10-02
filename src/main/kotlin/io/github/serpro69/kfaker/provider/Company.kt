package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.COMPANY] category.
 */
@Suppress("unused")
class Company internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.COMPANY

    val buzzwords = resolve { fakerService.resolve(it, "buzzwords") }
    val bs = resolve { fakerService.resolve(it, "bs") }
    val name = resolve { fakerService.resolve(it, "name") }
    val industry = resolve { fakerService.resolve(it, "industry") }
    val profession = resolve { fakerService.resolve(it, "profession") }
    val type = resolve { fakerService.resolve(it, "type") }
}
