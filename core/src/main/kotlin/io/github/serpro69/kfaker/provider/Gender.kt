package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.GENDER] category.
 */
@Suppress("unused")
class Gender internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Gender>(fakerService) {
    override val category = YamlCategory.GENDER
    override val localUniqueDataProvider = LocalUniqueDataProvider<Gender>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun types() = resolve("types")
    fun binaryTypes() = resolve("binary_types")
    fun shortBinaryTypes() = resolve("short_binary_types")
}
