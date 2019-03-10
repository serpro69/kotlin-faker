package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.LOVECRAFT] category.
 */
class Lovecraft internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.LOVECRAFT

    val fhtagn = resolve { fakerService.resolve(Faker, it, "fhtagn") }
    val deity = resolve { fakerService.resolve(Faker, it, "deity") }
    val location = resolve { fakerService.resolve(Faker, it, "location") }
    val tome = resolve { fakerService.resolve(Faker, it, "tome") }
    val words = resolve { fakerService.resolve(Faker, it, "words") }
}
