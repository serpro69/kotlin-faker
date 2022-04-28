package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.COFFEE] category.
 */
@Suppress("unused")
class Coffee internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Coffee>(fakerService) {
    override val category = YamlCategory.COFFEE
    override val localUniqueDataProvider = LocalUniqueDataProvider<Coffee>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun country() = resolve("country")
    fun regions(country: String) = resolve("regions", country.lowercase())
    fun variety() = resolve("variety")
    fun notes() = resolve("notes")
    fun blendName() = resolve("blend_name")
}
