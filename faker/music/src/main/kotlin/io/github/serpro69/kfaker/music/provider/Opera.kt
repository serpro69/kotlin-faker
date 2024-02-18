package io.github.serpro69.kfaker.music.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.OPERA] category.
 */
@Suppress("unused")
class Opera internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Opera>(fakerService) {
    override val yamlCategory = YamlCategory.OPERA
    override val localUniqueDataProvider = LocalUniqueDataProvider<Opera>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    val italian by lazy { ItalianOpera(fakerService) }

    val german by lazy { GermanOpera(fakerService) }

    val french by lazy { FrenchOpera(fakerService) }

    // TODO: 24.11.2019 fun italian() // resolves to a random italian opera
    // TODO: fun german() // resolves to a random german opera
    // TODO: fun french() // resolves to a random french opera
}

class ItalianOpera internal constructor(
    fakerService: FakerService
) : YamlFakeDataProvider<ItalianOpera>(fakerService) {
    override val yamlCategory = YamlCategory.OPERA
    override val localUniqueDataProvider = LocalUniqueDataProvider<ItalianOpera>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun byGiuseppeVerdi() = resolve("italian", "by_giuseppe_verdi")
    fun byGioacchinoRossini() = resolve("italian", "by_gioacchino_rossini")
    fun byGaetanoDonizetti() = resolve("italian", "by_gaetano_donizetti")
    fun byVincenzoBellini() = resolve("italian", "by_vincenzo_bellini")
    fun byChristophWillibaldGluck() = resolve("italian", "by_christoph_willibald_gluck")
    fun byWolfgangAmadeusMozart() = resolve("italian", "by_wolfgang_amadeus_mozart")
}

class GermanOpera internal constructor(
    fakerService: FakerService
) : YamlFakeDataProvider<GermanOpera>(fakerService) {
    override val yamlCategory = YamlCategory.OPERA
    override val localUniqueDataProvider = LocalUniqueDataProvider<GermanOpera>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun byWolfgangAmadeusMozart(): String = resolve("german", "by_wolfgang_amadeus_mozart")
    fun byLudwigVanBeethoven(): String = resolve("german", "by_ludwig_van_beethoven")
    fun byCarlMariaVonWeber(): String = resolve("german", "by_carl_maria_von_weber")
    fun byRichardStrauss(): String = resolve("german", "by_richard_strauss")
    fun byRichardWagner(): String = resolve("german", "by_richard_wagner")
    fun byRobertSchumann(): String = resolve("german", "by_robert_schumann")
    fun byFranzSchubert(): String = resolve("german", "by_franz_schubert")
    fun byAlbanBerg(): String = resolve("german", "by_alban_berg")
}

class FrenchOpera internal constructor(
    fakerService: FakerService
) : YamlFakeDataProvider<FrenchOpera>(fakerService) {
    override val yamlCategory = YamlCategory.OPERA
    override val localUniqueDataProvider = LocalUniqueDataProvider<FrenchOpera>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun byChristophWillibaldGluck(): String = resolve("french", "by_christoph_willibald_gluck")
    fun byMauriceRavel(): String = resolve("french", "by_maurice_ravel")
    fun byHectorBerlioz(): String = resolve("french", "by_hector_berlioz")
    fun byGeorgesBizet(): String = resolve("french", "by_georges_bizet")
    fun byCharlesGounod(): String = resolve("french", "by_charles_gounod")
    fun byCamilleSaintSaens(): String = resolve("french", "by_camille_saint_saens")
}
