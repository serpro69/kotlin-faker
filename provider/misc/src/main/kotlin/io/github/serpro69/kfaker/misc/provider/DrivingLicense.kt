package io.github.serpro69.kfaker.misc.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.DRIVING_LICENSE] category.
 */
@Suppress("unused")
class DrivingLicense internal constructor(fakerService: FakerService) : YamlFakeDataProvider<DrivingLicense>(fakerService) {
    override val yamlCategory = YamlCategory.DRIVING_LICENSE
    override val localUniqueDataProvider = LocalUniqueDataProvider<DrivingLicense>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun license() = with(fakerService) { resolve("usa", "").numerify().letterify() }
    fun licenseByState(state: String) = with(fakerService) { resolve("usa", state).numerify().letterify() }
}
