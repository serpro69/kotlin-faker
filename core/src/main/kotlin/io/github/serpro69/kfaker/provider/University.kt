package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.UNIVERSITY] category.
 */
@Suppress("unused")
class University internal constructor(fakerService: FakerService) : YamlFakeDataProvider<University>(fakerService) {
    override val yamlCategory = YamlCategory.UNIVERSITY
    override val localUniqueDataProvider = LocalUniqueDataProvider<University>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    // Prefix and suffix are needed because they are used as expressions, i.e. `#{University.prefix}`

    fun prefix() = resolve("prefix")
    fun suffix() = resolve("suffix")
    fun name() = resolve("name")
}
