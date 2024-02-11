package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.helper.prepare
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate

/**
 * [FakeDataProvider] implementation for [YamlCategory.INTERNET] category.
 */
@Suppress("unused")
class Internet internal constructor(fakerService: FakerService) : YamlFakeDataProvider<Internet>(fakerService) {
    override val yamlCategory = YamlCategory.INTERNET
    override val localUniqueDataProvider = LocalUniqueDataProvider<Internet>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun domain(subdomain: Boolean = false, domain: String? = null): String {
        val name: () -> String = {
            prepare(fakerService.faker.company.name().split(" ").first(), fakerService.faker.config)
        }
        return domain?.let {
            domain.split(".")
                .map { domainPart -> prepare(domainPart, fakerService.faker.config) }
                .toMutableList()
                .also {
                    if (it.size < 2) it.add(safeDomainSuffix())
                    if (subdomain && it.size < 3) it.add(0, prepare(name(), fakerService.faker.config))
                }.joinToString(".")
        } ?: run {
            mutableListOf(name(), safeDomainSuffix())
                .also { if (subdomain) it.add(0, prepare(name(), fakerService.faker.config)) }
                .joinToString(".")
        }
    }

    @JvmOverloads
    fun email(name: String = ""): String {
        val localName = if (name.trim() == "") {
            fakerService.faker.name.name()
                .replace(".", "")
                .replace(" ", ".")
                .lowercase()
        } else name.replace(" ", "")

        return "$localName@${domain()}"
    }

    @JvmOverloads
    fun safeEmail(name: String = "") = "${email(name).substringBeforeLast(".")}.test"

    fun slug(): String = resolve("slug")

    fun domainSuffix(): String = resolve("domain_suffix")
    fun safeDomainSuffix(): String = resolve("safe_domain_suffix")
    fun userAgent(browserType: String): String = resolve("user_agent", browserType.lowercase())
    fun botUserAgent(type: String): String = resolve("bot_user_agent", type.lowercase())
}
