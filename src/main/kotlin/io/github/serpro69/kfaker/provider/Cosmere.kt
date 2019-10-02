package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.COSMERE] category.
 */
class Cosmere internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.COSMERE

    val aons = resolve { fakerService.resolve(it, "aons") }
    val shardWorlds = resolve { fakerService.resolve(it, "shard_worlds") }
    val shards = resolve { fakerService.resolve(it, "shards") }
    val surges = resolve { fakerService.resolve(it, "surges") }
    val knightsRadiant = resolve { fakerService.resolve(it, "knights_radiant") }
    val metals = resolve { fakerService.resolve(it, "metals") }
    val allomancers = resolve { fakerService.resolve(it, "allomancers") }
    val feruchemists = resolve { fakerService.resolve(it, "feruchemists") }
}
