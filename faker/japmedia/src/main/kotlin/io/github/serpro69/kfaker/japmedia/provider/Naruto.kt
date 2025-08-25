package io.github.serpro69.kfaker.japmedia.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/** [FakeDataProvider] implementation for [YamlCategory.NARUTO] category. */
@Suppress("unused")
class Naruto internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<Naruto>(fakerService) {
    override val yamlCategory = YamlCategory.NARUTO
    override val localUniqueDataProvider = LocalUniqueDataProvider<Naruto>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun characters(): String = resolve("characters")

    fun villages(): String = resolve("villages")

    fun eyes(): String = resolve("eyes")

    fun demons(): String = resolve("demons")
}
