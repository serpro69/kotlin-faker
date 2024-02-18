package io.github.serpro69.kfaker.lorem.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.SLACK_EMOJI] category.
 */
@Suppress("unused")
class SlackEmoji internal constructor(fakerService: FakerService) : YamlFakeDataProvider<SlackEmoji>(fakerService) {
    override val yamlCategory = YamlCategory.SLACK_EMOJI
    override val localUniqueDataProvider = LocalUniqueDataProvider<SlackEmoji>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun people() = resolve("people")
    fun nature() = resolve("nature")
    fun foodAndDrink() = resolve("food_and_drink")
    fun celebration() = resolve("celebration")
    fun activity() = resolve("activity")
    fun travelAndPlaces() = resolve("travel_and_places")
    fun objectsAndSymbols() = resolve("objects_and_symbols")
    fun custom() = resolve("custom")
    fun emoji() = resolve("emoji")
}
