@file:Suppress("unused")

package io.github.serpro69.kfaker.movies.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/** [FakeDataProvider] implementation for [YamlCategory.AVATAR] */
class Avatar internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<Avatar>(fakerService) {
    override val yamlCategory = YamlCategory.AVATAR
    override val localUniqueDataProvider = LocalUniqueDataProvider<Avatar>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun characters() = resolve("characters")

    fun dates() = resolve("dates")

    fun quotes() = resolve("quotes")
}
