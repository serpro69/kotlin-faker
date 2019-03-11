package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.FILE] category.
 */
class File internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.FILE

    val extension = resolve { fakerService.resolve(Faker, it, "extension") }
    val mimeType = resolve { fakerService.resolve(Faker, it, "mime_type") }
}
