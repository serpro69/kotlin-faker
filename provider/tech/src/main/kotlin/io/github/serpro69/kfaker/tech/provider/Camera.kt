@file:Suppress("unused")

package io.github.serpro69.kfaker.tech.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.CAMERA] category.
 */
class Camera internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Camera>(fakerService) {
    override val yamlCategory = YamlCategory.CAMERA
    override val localUniqueDataProvider = LocalUniqueDataProvider<Camera>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun brand() = resolve("brand")
    fun model() = resolve("model")
    fun brandWithModel() = resolve("brand_with_model")
}

