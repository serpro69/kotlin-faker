package io.github.serpro69.kfaker.music.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.SHOW] category.
 */
@Suppress("unused")
class Theater internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Theater>(fakerService) {
    override val yamlCategory = YamlCategory.THEATER
    override val localUniqueDataProvider = LocalUniqueDataProvider<Theater>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun adultMusical() = resolve("adult_musical")
    fun play() = resolve("play")
    fun kidsMusical() = resolve("kids_musical")
}
