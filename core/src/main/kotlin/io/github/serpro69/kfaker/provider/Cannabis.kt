package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.CANNABIS] category.
 */
@Suppress("unused")
class Cannabis internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Cannabis>(fakerService) {
    override val categoryName = CategoryName.CANNABIS
    override val localUniqueDataProvider = LocalUniqueDataProvider<Cannabis>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun strains() = resolve("strains")
    fun cannabinoidAbbreviations() = resolve("cannabinoid_abbreviations")
    fun cannabinoids() = resolve("cannabinoids")
    fun terpenes() = resolve("terpenes")
    fun medicalUses() = resolve("medical_uses")
    fun healthBenefits() = resolve("health_benefits")
    fun categories() = resolve("categories")
    fun types() = resolve("types")
    fun brands() = resolve("brands")
}
