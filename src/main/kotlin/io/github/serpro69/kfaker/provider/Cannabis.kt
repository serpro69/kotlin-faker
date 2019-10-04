package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.CANNABIS] category.
 */
@Suppress("unused")
class Cannabis internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Cannabis>(fakerService) {
    override val categoryName = CategoryName.CANNABIS
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val strains = resolve("strains")
    val cannabinoidAbbreviations = resolve("cannabinoid_abbreviations")
    val cannabinoids = resolve("cannabinoids")
    val terpenes = resolve("terpenes")
    val medicalUses = resolve("medical_uses")
    val healthBenefits = resolve("health_benefits")
    val categories = resolve("categories")
    val types = resolve("types")
}
