package io.github.serpro69.kfaker.misc.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.GREEK_PHILOSOPHERS] category.
 */
@Suppress("unused")
class GreekPhilosophers internal constructor(fakerService: FakerService) : YamlFakeDataProvider<GreekPhilosophers>(fakerService) {
    override val yamlCategory = YamlCategory.GREEK_PHILOSOPHERS
    override val localUniqueDataProvider = LocalUniqueDataProvider<GreekPhilosophers>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun names() = resolve("names")
    fun quotes() = resolve("quotes")
}
