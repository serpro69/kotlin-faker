package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.COSMERE] category.
 */
@Suppress("unused")
class Cosmere internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Cosmere>(fakerService) {
    override val categoryName = CategoryName.COSMERE
    override val localUniqueDataProvider = LocalUniqueDataProvider<Cosmere>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    fun aons() = resolve("aons")
    fun shardWorlds() = resolve("shard_worlds")
    fun shards() = resolve("shards")
    fun surges() = resolve("surges")
    fun knightsRadiant() = resolve("knights_radiant")
    fun metals() = resolve("metals")
    fun allomancers() = resolve("allomancers")
    fun feruchemists() = resolve("feruchemists")
}
