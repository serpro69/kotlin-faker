package io.github.serpro69.kfaker.app.subcommands

import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.FakerConfig
import io.github.serpro69.kfaker.app.cli.Introspector
import io.github.serpro69.kfaker.app.cli.Renderer
import io.github.serpro69.kfaker.create
import picocli.CommandLine
import kotlin.system.exitProcess

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

    private fun printProvidersList() {
        val fakerConfig = FakerConfig.builder().create {
            locale = options.locale
        }

        val faker = Faker(fakerConfig)

        val introspector = Introspector(faker)

        val renderedProviders = if (options.verbose) {
            introspector.providerFunctions.map { (provider, functions) ->
                val renderedFunctions = functions.map {
                    val value = when (it.parameters.size) {
                        1 -> it.call(provider.getter.call(faker)).toString()
                        2 -> it.call(provider.getter.call(faker), "").toString()
                        3 -> it.call(provider.getter.call(faker), "", "").toString()
                        else -> exitProcess(3)
                    }

                    Renderer("${it.name}() // => $value", emptyList())
                }

                Renderer(provider.name, renderedFunctions)
            }
        } else {
            introspector.providerFunctions.map { (provider, functions) ->
                val renderedFunctions = functions.map {
                    Renderer("${it.name}()", emptyList())
                }

                Renderer(provider.name, renderedFunctions)
            }
        }

        val output = Renderer("Faker()", renderedProviders).toString()

        println(output)
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
        if (listLocales) printAvailableLocales() else printProvidersList()
    }
}
