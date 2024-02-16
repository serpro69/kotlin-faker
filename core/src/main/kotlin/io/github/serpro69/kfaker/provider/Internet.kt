package io.github.serpro69.kfaker.provider

import io.github.serpro69.kfaker.FakerService
import io.github.serpro69.kfaker.dictionary.YamlCategory
import io.github.serpro69.kfaker.faker
import io.github.serpro69.kfaker.helper.isReservedNet
import io.github.serpro69.kfaker.helper.prepare
import io.github.serpro69.kfaker.provider.unique.LocalUniqueDataProvider
import io.github.serpro69.kfaker.provider.unique.UniqueProviderDelegate
import java.lang.String.format

/**
 * [FakeDataProvider] implementation for [YamlCategory.INTERNET] category.
 */
@Suppress("unused")
class Internet internal constructor(
    fakerService: FakerService,
    private val nameProvider: Name,
) : YamlFakeDataProvider<Internet>(fakerService) {
    override val yamlCategory = YamlCategory.INTERNET
    override val localUniqueDataProvider = LocalUniqueDataProvider<Internet>()
    override val unique by UniqueProviderDelegate(localUniqueDataProvider)

    init {
        fakerService.load(yamlCategory)
    }

    fun domain(subdomain: Boolean = false, domain: String? = null): String {
        val name: () -> String = { prepare(nameProvider.lastName().split(" ").first(), fakerService.faker.config) }
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

    /**
     * Returns a random IPv4 address
     */
    fun iPv4Address() = List(4) { fakerService.randomService.nextInt(0, 255) }
        .joinToString(".")

    /**
     * Returns a random private IPv4 address
     */
    fun privateIPv4Address(): String {
        val ranges = fakerService.randomService.randomValue(privateIpv4Ranges)
        return ranges.map { fakerService.randomService.nextInt(it) }.joinToString(".")
    }

    /**
     * Returns a random public IPv4 address
     */
    fun publicIPv4Address(): String = with(iPv4Address()) {
        if (isReservedNet(this)) publicIPv4Address() else this
    }

    /**
     * Returns a random IPv6 address
     *
     * Example:
     * ```
     * Faker().internet.iPv6Address() // => 176f:cfec:c73b:e0cb:534d:4b3e:db4e:3b53
     * ```
     */
    fun iPv6Address(): String = List(8) { fakerService.randomService.nextInt(65_536).toString(16) }
        .joinToString(":")

    /**
     * Returns a random mac-address with an optional [prefix]
     *
     * Examples:
     * ```
     * Faker().internet.macAddress() // => 17:12:d9:fc:fe:f6
     * Faker().internet.macAddress("a") // => 0a:11:ed:7c:b5:af
     * Faker().internet.macAddress("aa") // => aa:ec:eb:54:b9:f5
     * Faker().internet.macAddress("aa:ce") // => aa:ce:e3:e1:83:c4
     *
     * ```
     */
    @OptIn(ExperimentalStdlibApi::class)
    fun macAddress(prefix: String = ""): String {
        val pref = prefix.split(":").mapNotNull { if (it.isNotBlank()) it.hexToInt() else null }
        val addr = List(6 - pref.size) { fakerService.randomService.nextInt(256) }
        return listOf(pref, addr).flatten().joinToString(":") { format("%02x", it) }
    }

    @JvmOverloads
    fun email(name: String = ""): String {
        val localName = if (name.trim() == "") {
            nameProvider.name()
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

// Private, Host, and Link-Local network address blocks as defined in https://en.wikipedia.org/wiki/IPv4#Special-use_addresses
private val privateIpv4Ranges = listOf(
    // 10.0.0.0/8     - Used for local communications within a private network
    listOf(10..10, 0..255, 0..255, 1..255),
    // 100.64.0.0/10  - Shared address space for communications between an ISP and its subscribers
    listOf(100..100, 64..127, 0..255, 1..255),
    // 127.0.0.0/8    - Used for loopback addresses to the local host
    listOf(127..127, 0..255, 0..255, 1..255),
    // 169.254.0.0/16 - Used for link-local addresses between two hosts on a single link when
    listOf(169..169, 254..254, 0..255, 1..255),
    // 172.16.0.0/12  - Used for local communications within a private network
    listOf(172..172, 16..31, 0..255, 1..255),
    // 192.0.0.0/24   - IETF Protocol Assignments
    listOf(192..192, 0..0, 0..0, 1..255),
    // 192.168.0.0/16 - Used for local communications within a private network
    listOf(192..192, 168..168, 0..255, 1..255),
    // 198.18.0.0/15  - Used for benchmark testing of inter-network communications between subnets
    listOf(198..198, 18..19, 0..255, 1..255)
)

fun main() {
    val f = faker {  }
    repeat(100) {
        println(f.internet.domain())
    }
}
