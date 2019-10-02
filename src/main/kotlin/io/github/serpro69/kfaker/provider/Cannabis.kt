package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.CANNABIS] category.
 */
class Cannabis internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.CANNABIS

    val strains = resolve { fakerService.resolve(it, "strains") }
    val cannabinoidAbbreviations = resolve { fakerService.resolve(it, "cannabinoid_abbreviations") }
    val cannabinoids = resolve { fakerService.resolve(it, "cannabinoids") }
    val terpenes = resolve { fakerService.resolve(it, "terpenes") }
    val medicalUses = resolve { fakerService.resolve(it, "medical_uses") }
    val healthBenefits = resolve { fakerService.resolve(it, "health_benefits") }
    val categories = resolve { fakerService.resolve(it, "categories") }
    val types = resolve { fakerService.resolve(it, "types") }
}
