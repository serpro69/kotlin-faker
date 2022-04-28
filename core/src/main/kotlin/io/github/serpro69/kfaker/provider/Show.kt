package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.SHOW] category.
 */
@Suppress("unused")
class Show internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Show>(fakerService) {
    override val category = YamlCategory.SHOW
    override val localUniqueDataProvider = LocalUniqueDataProvider<Show>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun adultMusical() = resolve("adult_musical")
    fun play() = resolve("play")
    fun kidsMusical() = resolve("kids_musical")
}
