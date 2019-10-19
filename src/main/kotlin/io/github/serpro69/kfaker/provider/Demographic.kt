package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.DEMOGRAPHIC] category.
 */
@Suppress("unused")
class Demographic internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Demographic>(fakerService) {
    override val categoryName = CategoryName.DEMOGRAPHIC
    override val localUniqueDataProvider = LocalUniqueDataProvider<Demographic>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun race() = resolve("race")
    fun sex() = resolve("sex")
    fun demonym() = resolve("demonym")
    fun educationalAttainment() = resolve("educational_attainment")
    fun maritalStatus() = resolve("marital_status")
}
