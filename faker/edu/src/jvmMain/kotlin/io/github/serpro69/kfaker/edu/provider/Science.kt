package io.github.serpro69.kfaker.edu.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.provider.FakeDataProvider
import io.github.serpro69.kfaker.provider.YamlFakeDataProvider
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/** [FakeDataProvider] implementation for [YamlCategory.SCIENCE] category. */
@Suppress("unused")
class Science internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<Science>(fakerService) {
    override val yamlCategory = YamlCategory.SCIENCE
    override val localUniqueDataProvider = LocalUniqueDataProvider<Science>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    val branch by lazy { ScienceBranch(fakerService) }

    fun element() = resolve("element")

    fun elementSymbol() = resolve("element_symbol")

    fun elementState() = resolve("element_state")

    fun elementSubcategory() = resolve("element_subcategory")

    fun modifier() = resolve("modifier")

    fun scientist() = resolve("scientist")

    fun tool() = resolve("tool")
}

class ScienceBranch internal constructor(fakerService: FakerService) :
    YamlFakeDataProvider<ScienceBranch>(fakerService) {
    override val yamlCategory = YamlCategory.SCIENCE
    override val localUniqueDataProvider = LocalUniqueDataProvider<ScienceBranch>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider, fakerService)

    init {
        fakerService.load(yamlCategory)
    }

    fun empiricalNaturalBasic(): String = resolve("branch", "empirical_natural_basic")

    fun empiricalNaturalApplied(): String = resolve("branch", "empirical_natural_applied")

    fun empiricalSocialBasic(): String = resolve("branch", "empirical_social_basic")

    fun empiricalSocialApplied(): String = resolve("branch", "empirical_social_applied")

    fun formalBasic(): String = resolve("branch", "formal_basic")

    fun formalApplied(): String = resolve("branch", "formal_applied")
}
