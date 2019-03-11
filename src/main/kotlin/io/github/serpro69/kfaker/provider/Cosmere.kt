package io.github.sergio.igwt.kfaker.provider

import io.github.sergio.igwt.kfaker.Faker
import io.github.sergio.igwt.kfaker.FakerService
import io.github.sergio.igwt.kfaker.dictionary.CategoryName

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
