package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.SHAKESPEARE] category.
 */
@Suppress("unused")
class Shakespeare internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Shakespeare>(fakerService) {
    override val yamlCategory = YamlCategory.SHAKESPEARE
    override val localUniqueDataProvider = LocalUniqueDataProvider<Shakespeare>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun hamlet() = resolve("hamlet")
    fun asYouLikeIt() = resolve("as_you_like_it")
    fun kingRichardTheThird() = resolve("king_richard_iii")
    fun romeoAndJuliet() = resolve("romeo_and_juliet")
}
