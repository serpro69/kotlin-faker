package io.github.serpro69.kfaker.tv.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.SILICON_VALLEY] category.
 */
@Suppress("unused")
class SiliconValley internal constructor(fakerService: FakerService) : YamlFakeDataProvider<SiliconValley>(fakerService) {
    override val yamlCategory = YamlCategory.SILICON_VALLEY
    override val localUniqueDataProvider = LocalUniqueDataProvider<SiliconValley>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun characters() = resolve("characters")
    fun companies() = resolve("companies")
    fun quotes() = resolve("quotes")
    fun apps() = resolve("apps")
    fun inventions() = resolve("inventions")
    fun mottos() = resolve("mottos")
    fun urls() = resolve("urls")
    fun email() = resolve("email")
}
