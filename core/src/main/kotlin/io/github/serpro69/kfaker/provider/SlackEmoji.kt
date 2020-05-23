package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.dictionary.*

/**
 * [FakeDataProvider] implementation for [CategoryName.SLACK_EMOJI] category.
 */
@Suppress("unused")
class SlackEmoji internal constructor(fakerService: FakerService) : AbstractFakeDataProvider<SlackEmoji>(fakerService) {
    override val categoryName = CategoryName.SLACK_EMOJI
    override val localUniqueDataProvider = LocalUniqueDataProvider<SlackEmoji>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

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
