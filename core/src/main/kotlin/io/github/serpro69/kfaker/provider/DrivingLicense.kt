package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.DRIVING_LICENCE] category.
 */
@Suppress("unused")
class DrivingLicense internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<DrivingLicense>(fakerService) {
    override val category = YamlCategory.DRIVING_LICENCE
    override val localUniqueDataProvider = LocalUniqueDataProvider<DrivingLicense>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun license() = with(fakerService) { resolve("usa", "").numerify().letterify() }
    fun licenseByState(state: String) = with(fakerService) { resolve("usa", state).numerify().letterify() }
}
