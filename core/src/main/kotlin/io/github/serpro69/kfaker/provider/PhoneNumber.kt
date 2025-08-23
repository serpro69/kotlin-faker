@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.PHONE_NUMBER] category.
 */
class PhoneNumber internal constructor(fakerService: FakerService) : YamlFakeDataProvider<PhoneNumber>(fakerService) {
    override val yamlCategory = YamlCategory.PHONE_NUMBER
    override val localUniqueDataProvider = LocalUniqueDataProvider<PhoneNumber>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    val cellPhone by lazy { CellPhone(fakerService) }

    fun areaCode() = resolve("area_code")
    fun countryCode() = resolve("country_code")
    fun exchangeCode() = resolve("exchange_code")
    fun phoneNumber() = with(fakerService) { resolve("formats").numerify() }
    fun extension() = with(fakerService) { resolve("extension").numerify() }
    fun subscriberNumber() = with(fakerService) { resolve("subscriber_number").numerify() }
}

/**
 * [FakeDataProvider] implementation for [YamlCategory.CELL_PHONE] category.
 */
class CellPhone internal constructor(fakerService: FakerService) : YamlFakeDataProvider<CellPhone>(fakerService) {
    override val yamlCategory = YamlCategory.CELL_PHONE
    override val localUniqueDataProvider = LocalUniqueDataProvider<CellPhone>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun number() = with(fakerService) { resolve("formats").numerify() }
}
