package io.github.serpro69.kfaker.travel.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.AIRPORT] category.
 */
@Suppress("unused")
class Airport internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Airport>(fakerService) {
    override val yamlCategory = YamlCategory.AIRPORT
    override val localUniqueDataProvider = LocalUniqueDataProvider<Airport>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    val unitedStates by lazy { UnitedStates(fakerService) }
    val europeanUnion by lazy { EuropeanUnion(fakerService) }
}

/**
 * [FakeDataProvider] implementation for [YamlCategory.AIRPORT] category.
 */
class UnitedStates internal constructor(fakerService: FakerService) : YamlFakeDataProvider<UnitedStates>(fakerService) {
    override val yamlCategory = YamlCategory.AIRPORT
    override val localUniqueDataProvider = LocalUniqueDataProvider<UnitedStates>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    val iataCode by lazy { IataCode(fakerService, "united_states") }

    fun large() = resolve("united_states", "large")
    fun medium() = resolve("united_states", "medium")
    fun small() = resolve("united_states", "small")
}

/**
 * [FakeDataProvider] implementation for [YamlCategory.AIRPORT] category.
 */
class EuropeanUnion internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<EuropeanUnion>(fakerService) {
    override val yamlCategory = YamlCategory.AIRPORT
    override val localUniqueDataProvider = LocalUniqueDataProvider<EuropeanUnion>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    val iataCode by lazy {
        object : IataCode(fakerService, "european_union") {
            override fun small(): String {
                throw NoSuchElementException("airport.european_union.iata_code does not contain values for 'small' key")
            }
        }
    }

    fun large() = resolve("european_union", "large")
    fun medium() = resolve("european_union", "medium")
}

/**
 * [FakeDataProvider] implementation for [YamlCategory.AIRPORT] category.
 */
open class IataCode internal constructor(fakerService: FakerService, private val country: String) :
    YamlFakeDataProvider<IataCode>(fakerService) {
    final override val yamlCategory = YamlCategory.AIRPORT
    final override val localUniqueDataProvider = LocalUniqueDataProvider<IataCode>()
    final override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun large() = resolve(country, "iata_code", "large")
    fun medium() = resolve(country, "iata_code", "medium")
    open fun small() = resolve(country, "iata_code", "small")
}
