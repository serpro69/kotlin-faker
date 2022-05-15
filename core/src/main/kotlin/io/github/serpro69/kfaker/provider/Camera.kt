@file:Suppress("unused")

package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.CAMERA] category.
 */
class Camera internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Camera>(fakerService) {
    override val category = YamlCategory.CAMERA
    override val localUniqueDataProvider = LocalUniqueDataProvider<Camera>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun brand() = resolve("brand")
    fun model() = resolve("model")
    fun brandWithModel() = resolve("brand_with_model")
}

