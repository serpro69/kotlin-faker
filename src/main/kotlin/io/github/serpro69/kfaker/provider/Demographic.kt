package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.DEMOGRAPHIC] category.
 */
@Suppress("unused")
class Demographic internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Demographic>(fakerService) {
    override val categoryName = CategoryName.DEMOGRAPHIC
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val race = resolve("race")
    val sex = resolve("sex")
    val demonym = resolve("demonym")
    val educationalAttainment = resolve("educational_attainment")
    val maritalStatus = resolve("marital_status")
}
