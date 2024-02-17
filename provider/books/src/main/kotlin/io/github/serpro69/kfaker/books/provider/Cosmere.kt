package io.github.serpro69.kfaker.books.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.COSMERE] category.
 */
@Suppress("unused")
class Cosmere internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Cosmere>(fakerService) {
    override val yamlCategory = YamlCategory.COSMERE
    override val localUniqueDataProvider = LocalUniqueDataProvider<Cosmere>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun aons() = resolve("aons")
    fun shardWorlds() = resolve("shard_worlds")
    fun shards() = resolve("shards")
    fun surges() = resolve("surges")
    fun knightsRadiant() = resolve("knights_radiant")
    fun metals() = resolve("metals")
    fun allomancers() = resolve("allomancers")
    fun feruchemists() = resolve("feruchemists")
}
