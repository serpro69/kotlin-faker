package io.github.serpro69.kfaker.databases.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.RandomService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/** [FakeDataProvider] implementation for [YamlCategory.DATABASE] category. */
@Suppress("unused")
class Database
internal constructor(fakerService: FakerService, private val randomService: RandomService) :
    YamlFakeDataProvider<Database>(fakerService) {
    override val yamlCategory = YamlCategory.DATABASE
    override val localUniqueDataProvider = LocalUniqueDataProvider<Database>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun columnName() = resolve("column_name")

    fun mongodbObjectId() =
        resolveUniqueValue("objectId") {
            val date = randomService.randomPastDate()
            val epochSeconds = date.toInstant().toEpochMilli() / 1000
            val epochSecondsInHexa = epochSeconds.toString(16)

            "${epochSecondsInHexa}0000000000000000"
        }
}
