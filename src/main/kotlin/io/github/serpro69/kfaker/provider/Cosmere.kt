package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.COSMERE] category.
 */
class Cosmere internal constructor(fakerService: FakerService) : AbstractFakeDataProvider(fakerService) {
    override val categoryName = CategoryName.COSMERE

    val aons = resolve { fakerService.resolve(Faker, it, "aons") }
    val shardWorlds = resolve { fakerService.resolve(Faker, it, "shard_worlds") }
    val shards = resolve { fakerService.resolve(Faker, it, "shards") }
    val surges = resolve { fakerService.resolve(Faker, it, "surges") }
    val knightsRadiant = resolve { fakerService.resolve(Faker, it, "knights_radiant") }
    val metals = resolve { fakerService.resolve(Faker, it, "metals") }
    val allomancers = resolve { fakerService.resolve(Faker, it, "allomancers") }
    val feruchemists = resolve { fakerService.resolve(Faker, it, "feruchemists") }
}
