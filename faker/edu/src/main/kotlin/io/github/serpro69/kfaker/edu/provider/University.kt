package io.github.serpro69.kfaker.edu.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.UNIVERSITY] category.
 */
@Suppress("unused")
class University internal constructor(fakerService: FakerService) : YamlFakeDataProvider<University>(fakerService) {
    override val yamlCategory = YamlCategory.UNIVERSITY
    override val localUniqueDataProvider = LocalUniqueDataProvider<University>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    // Prefix and suffix are needed because they are used as expressions, i.e. `#{University.prefix}`

    fun prefix() = resolve("prefix")
    fun suffix() = resolve("suffix")
    fun name() = resolve("name")
}
