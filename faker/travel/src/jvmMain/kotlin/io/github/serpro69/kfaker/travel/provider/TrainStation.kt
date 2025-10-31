@file:Suppress("unused")

package io.github.serpro69.kfaker.travel.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/** [FakeDataProvider] implementation for [YamlCategory.TRAIN_STATION] category. */
class TrainStation internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<TrainStation>(fakerService) {
    override val yamlCategory = YamlCategory.TRAIN_STATION
    override val localUniqueDataProvider = LocalUniqueDataProvider<TrainStation>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    val germany by lazy { TrainStationGermany(fakerService) }
    val spain by lazy { TrainStationSpain(fakerService) }
    val unitedKingdom by lazy { TrainStationUnitedKingdom(fakerService) }
    val unitedStates by lazy { TrainStationUnitedStates(fakerService) }
}

class TrainStationGermany internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<TrainStationGermany>(fakerService) {
    override val yamlCategory = YamlCategory.TRAIN_STATION
    override val localUniqueDataProvider = LocalUniqueDataProvider<TrainStationGermany>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun metro(): String = resolve("germany", "metro")

    fun railway(): String = resolve("germany", "railway")
}

class TrainStationSpain internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<TrainStationSpain>(fakerService) {
    override val yamlCategory = YamlCategory.TRAIN_STATION
    override val localUniqueDataProvider = LocalUniqueDataProvider<TrainStationSpain>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun metro(): String = resolve("spain", "metro")

    fun railway(): String = resolve("spain", "railway")
}

class TrainStationUnitedKingdom internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<TrainStationUnitedKingdom>(fakerService) {
    override val yamlCategory = YamlCategory.TRAIN_STATION
    override val localUniqueDataProvider = LocalUniqueDataProvider<TrainStationUnitedKingdom>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun metro(): String = resolve("united_kingdom", "metro")

    fun railway(): String = resolve("united_kingdom", "railway")
}

class TrainStationUnitedStates internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<TrainStationUnitedStates>(fakerService) {
    override val yamlCategory = YamlCategory.TRAIN_STATION
    override val localUniqueDataProvider = LocalUniqueDataProvider<TrainStationUnitedStates>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun metro(): String = resolve("united_states", "metro")

    fun railway(): String = resolve("united_states", "railway")
}
