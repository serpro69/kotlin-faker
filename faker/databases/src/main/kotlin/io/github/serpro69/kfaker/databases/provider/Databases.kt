package io.github.serpro69.kfaker.databases.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.RandomService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.DATABASES] category.
 */
@Suppress("unused")
class Databases internal constructor(
    fakerService: FakerService,
    private val randomService: RandomService,
) : YamlFakeDataProvider<Databases>(fakerService) {
    override val yamlCategory = YamlCategory.DATABASES
    override val localUniqueDataProvider = LocalUniqueDataProvider<Databases>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun column() = resolve("column")

    fun mongodbObjectId() = resolveUniqueValue("objectId") {
        val date = randomService.nextPastDate()
        val epochSeconds = date.toInstant().toEpochMilli() / 1000
        val epochSecondsInHexa = epochSeconds.toString(16)

        "${epochSecondsInHexa}0000000000000000"
    }
}