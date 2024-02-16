package io.github.serpro69.kfaker.sports.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.CHESS] category.
 */
@Suppress("unused")
class Chess internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Chess>(fakerService) {
    override val yamlCategory = YamlCategory.CHESS
    override val localUniqueDataProvider = LocalUniqueDataProvider<Chess>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun players() = resolve("players")
    fun tournaments() = resolve("tournaments")
    fun openings() = resolve("openings")
    fun titles() = resolve("titles")
}
