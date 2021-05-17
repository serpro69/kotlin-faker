package io.github.serpro69.kfaker.app.subcommands

import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.app.KFaker
import io.github.serpro69.kfaker.app.cli.Introspector
import io.github.serpro69.kfaker.app.cli.Renderer
import io.github.serpro69.kfaker.app.cli.renderProvider
import io.github.serpro69.kfaker.app.subcommands.Lookup.functionName
import io.github.serpro69.kfaker.fakerConfig
import picocli.CommandLine

/**
 * [KFaker] command for looking up required functionality by [functionName]
 */
@CommandLine.Command(
    name = "lookup",
    description = ["lookup functions by name"],
    mixinStandardHelpOptions = true
)
object Lookup : Runnable {

    @CommandLine.Mixin
    private val options = CommandOptions()

    @CommandLine.Parameters(
        index = "0",
        description = ["function name to find in each provider", "case-insensitive"]
    )
    lateinit var functionName: String

    private fun printMatchingFunctions() {
        val fakerConfig = fakerConfig {
            locale = options.locale
        }

        val faker = Faker(fakerConfig)

        val introspector = Introspector(faker)

        val filteredMap = introspector.providerFunctions.mapValuesTo(mutableMapOf()) { (_, v) ->
            v.filter { it.name.toLowerCase().contains(functionName.toLowerCase()) }
        }.filterValues { it.isNotEmpty() }

        val renderedProviders = filteredMap.map { (provider, functions) ->
            renderProvider(options, faker, provider, functions)
        }

        val output = Renderer("Faker()", renderedProviders).toString()

        println(output)
    }

    override fun run() {
        printMatchingFunctions()
    }
}
