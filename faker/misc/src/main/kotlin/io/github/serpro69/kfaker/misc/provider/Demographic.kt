package io.github.serpro69.kfaker.misc.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.DEMOGRAPHIC] category.
 */
@Suppress("unused")
class Demographic internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Demographic>(fakerService) {
    override val yamlCategory = YamlCategory.DEMOGRAPHIC
    override val localUniqueDataProvider = LocalUniqueDataProvider<Demographic>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun race() = resolve("race")
    fun sex() = resolve("sex")
    fun demonym() = resolve("demonym")
    fun educationalAttainment() = resolve("educational_attainment")
    fun maritalStatus() = resolve("marital_status")
}
