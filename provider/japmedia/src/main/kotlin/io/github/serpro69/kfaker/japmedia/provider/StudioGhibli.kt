package io.github.serpro69.kfaker.japmedia.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.STUDIO_GHIBLI] category.
 */
@Suppress("unused")
class StudioGhibli internal constructor(fakerService: FakerService) : YamlFakeDataProvider<StudioGhibli>(fakerService) {
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
