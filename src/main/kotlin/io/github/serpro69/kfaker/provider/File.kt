package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.FILE] category.
 */
class File internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.FILE

    val extension = resolve { fakerService.resolve(Faker, it, "extension") }
    val mimeType = resolve { fakerService.resolve(Faker, it, "mime_type") }
}
