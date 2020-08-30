package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.DRIVING_LICENCE] category.
 */
@Suppress("unused")
class DrivingLicense internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<DrivingLicense>(fakerService) {
    override val categoryName = CategoryName.DRIVING_LICENCE
    override val localUniqueDataProvider = LocalUniqueDataProvider<DrivingLicense>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun license() = resolve("usa", "")
    fun licenseByState(state: String) = resolve("usa", state)
}