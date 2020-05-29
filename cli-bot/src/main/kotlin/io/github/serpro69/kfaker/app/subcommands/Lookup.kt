package io.github.serpro69.kfaker.app.subcommands

import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.FakerConfig
import io.github.serpro69.kfaker.app.cli.Introspector
import io.github.serpro69.kfaker.app.cli.Renderer
import io.github.serpro69.kfaker.create
import picocli.CommandLine
import kotlin.system.exitProcess

@CommandLine.Command(
    name = "lookup",
    description = ["lookup functions by name"]
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
        val fakerConfig = FakerConfig.builder().create {
            locale = options.locale
        }

        val faker = Faker(fakerConfig)

        val introspector = Introspector(faker)

        val filteredMap = introspector.providerFunctions.mapValuesTo(mutableMapOf()) { (_, v) ->
            v.filter { it.name.toLowerCase().contains(functionName.toLowerCase()) }
        }.filterValues { it.isNotEmpty() }

        val renderedProviders = if (options.verbose) {
            filteredMap.map { (provider, functions) ->
                val renderedFunctions = functions.map {
                    val value = when (it.parameters.size) {
                        1 -> it.invoke(provider.invoke(faker)).toString()
                        2 -> it.invoke(provider.invoke(faker), "").toString()
                        3 -> it.invoke(provider.invoke(faker), "", "").toString()
                        else -> exitProcess(3)
                    }

                    Renderer("${it.name}() // => $value", emptyList())
                }

                Renderer(provider.name, renderedFunctions)
            }
        } else {
            filteredMap.map { (provider, functions) ->
                val renderedFunctions = functions.map {
                    Renderer("${it.name}()", emptyList())
                }

                Renderer(provider.name, renderedFunctions)
            }
        }

        val output = Renderer("Faker()", renderedProviders).toString()

        println(output)
    }

    override fun run() {
        printMatchingFunctions()
    }
}
