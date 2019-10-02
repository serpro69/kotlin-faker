package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.NAME] category.
 */
@Suppress("unused")
class Name internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.NAME

    val maleFirstName = resolve { fakerService.resolve(it, "male_first_name") }
    val femaleFirstName = resolve { fakerService.resolve(it, "female_first_name") }
    val firstName = resolve { fakerService.resolve(it, "first_name") }
    val lastName = resolve { fakerService.resolve(it, "last_name") }
    val name = resolve { fakerService.resolve(it, "name") }
    val nameWithMiddle = resolve { fakerService.resolve(it, "name_with_middle") }
}