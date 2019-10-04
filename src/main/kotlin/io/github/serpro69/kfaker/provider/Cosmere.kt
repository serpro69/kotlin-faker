package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.COSMERE] category.
 */
@Suppress("unused")
class Cosmere internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<Cosmere>(fakerService) {
    override val categoryName = CategoryName.COSMERE
    override val unique by UniqueProviderDelegate(uniqueDataProvider)

    val aons = resolve("aons")
    val shardWorlds = resolve("shard_worlds")
    val shards = resolve("shards")
    val surges = resolve("surges")
    val knightsRadiant = resolve("knights_radiant")
    val metals = resolve("metals")
    val allomancers = resolve("allomancers")
    val feruchemists = resolve("feruchemists")
}
