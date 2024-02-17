package io.github.serpro69.kfaker.app.subcommands

import io.github.serpro69.kfaker.AbstractFaker
import io.github.serpro69.kfaker.app.KFaker
import io.github.serpro69.kfaker.app.cli.Introspector
import io.github.serpro69.kfaker.app.cli.Renderer
import io.github.serpro69.kfaker.app.cli.renderProvider
import io.github.serpro69.kfaker.app.fakers
import io.github.serpro69.kfaker.fakerConfig
import picocli.CommandLine

/**
 * [KFaker] command for listing available faker functionality
 */
@CommandLine.Command(
    name = "list",
    description = ["list available providers and their functions"],
    mixinStandardHelpOptions = true
)
object List : Runnable {

    @CommandLine.Mixin
    private val options = CommandOptions()

    @CommandLine.Option(
        names = ["--list-locales"],
        description = ["list available locales instead of providers", "if used other options will be ignored"],
        required = false
    )
    var listLocales: Boolean = false

    @CommandLine.Parameters(
        description = ["limit output to specified provider(s)", "case-insensitive"]
    )
    var providerNames = arrayOf<String>()

    private fun printProvidersList(faker: AbstractFaker) {
        val introspector = Introspector(faker)

        val renderedProviders = if (providerNames.isNotEmpty()) {
            introspector.providerData.asSequence().filter { (provider, _) ->
                providerNames.any { provider.name.lowercase().contains(it.lowercase() ) }
            }.map { (provider, fpPair) ->
                val (functions, properties) = fpPair
                renderProvider(options, faker, provider, null, functions, properties)
            }
        } else {
            introspector.providerData.asSequence().map { (provider, fpPair) ->
                val (functions, properties) = fpPair
                renderProvider(options, faker, provider, null, functions, properties)
            }
        }

        val output = Renderer("${faker::class.simpleName}()", renderedProviders.toList()).toString()

        println(output)
        println("\n")
    }

    private fun printAvailableLocales() {
        listOf(
            "ar", "bg", "ca", "ca-CAT", "da-DK", "de", "de-AT", "de-CH", "ee", "en", "en-AU", "en-au-ocker", "en-BORK",
            "en-CA", "en-GB", "en-IND", "en-MS", "en-NEP", "en-NG", "en-NZ", "en-PAK", "en-SG", "en-TH", "en-UG",
            "en-US", "en-ZA", "es", "es-MX", "fa", "fi-FI", "fr", "fr-CA", "fr-CH", "he", "hy", "id", "it", "ja", "ko",
            "lv", "nb-NO", "nl", "no", "pl", "pt", "pt-BR", "ru", "sk", "sv", "th", "tr", "uk", "vi", "zh-CN", "zh-TW"
        ).forEach { println(it) }
    }

    override fun run() {
        val fakerConfig = fakerConfig { locale = options.locale }
        if (listLocales) printAvailableLocales() else fakers(fakerConfig).forEach(::printProvidersList)
    }
}
