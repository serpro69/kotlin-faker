package io.github.serpro69.kfaker.games.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.SUPERHERO] category.
 */
@Suppress("unused")
class Superhero internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Superhero>(fakerService) {
    override val yamlCategory = YamlCategory.SUPERHERO
    override val localUniqueDataProvider = LocalUniqueDataProvider<Superhero>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    // These functions are needed because the value for `name` property
    // is resolved with these properties through yml expression in the form of `#{Superhero.prefix}`
    internal fun prefix() = resolve("prefix")
    internal fun suffix() = resolve("suffix")
    internal fun descriptor() = resolve("descriptor")

    fun power() = resolve("power")
    fun name() = resolve("name")
}
