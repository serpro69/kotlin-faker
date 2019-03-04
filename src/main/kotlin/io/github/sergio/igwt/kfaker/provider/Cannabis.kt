package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

/**
 * [FakeDataProvider] implementation for [CategoryName.CREATURE] category.
 */
class Cannabis internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.BUFFY

    val strains = resolve { fakerService.resolve(Faker, it, "strains") }
    val cannabinoidAbbreviations = resolve { fakerService.resolve(Faker, it, "cannabinoid_abbreviations") }
    val cannabinoids = resolve { fakerService.resolve(Faker, it, "cannabinoids") }
    val terpenes = resolve { fakerService.resolve(Faker, it, "terpenes") }
    val medicalUses = resolve { fakerService.resolve(Faker, it, "medical_uses") }
    val healthBenefits = resolve { fakerService.resolve(Faker, it, "health_benefits") }
    val categories = resolve { fakerService.resolve(Faker, it, "categories") }
    val types = resolve { fakerService.resolve(Faker, it, "types") }
}
