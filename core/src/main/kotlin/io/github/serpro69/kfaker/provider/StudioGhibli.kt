@file:Suppress("unused")

package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.STUDIO_GHIBLI] category.
 */
class StudioGhibli internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<StudioGhibli>(fakerService) {
    override val yamlCategory = YamlCategory.STUDIO_GHIBLI
    override val localUniqueDataProvider = LocalUniqueDataProvider<StudioGhibli>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun characters(): String = resolve("characters")
    fun quotes(): String = resolve("quotes")
    fun movies(): String = resolve("movies")
}
