package io.github.serpro69.kfaker.app.subcommands

import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.app.KFaker
import io.github.serpro69.kfaker.app.cli.Introspector
import io.github.serpro69.kfaker.app.cli.Renderer
import io.github.serpro69.kfaker.app.cli.renderProvider
import io.github.serpro69.kfaker.app.subcommands.Lookup.name
import io.github.serpro69.kfaker.fakerConfig
import picocli.CommandLine
import kotlin.reflect.KFunction
import kotlin.reflect.KProperty

/**
 * [KFaker] command for looking up required functionality by [name]
 */
@CommandLine.Command(
    name = "lookup",
    description = ["lookup providers and functions by name"],
    mixinStandardHelpOptions = true
)
object Lookup : Runnable {

    @CommandLine.Mixin
    private val options = CommandOptions()

    @CommandLine.Parameters(
        index = "0",
        description = ["name of the provider and/or provider function(s)", "partial name matching, case-insensitive"]
    )
    lateinit var name: String

    private fun printMatchingFunctions() {
        val fakerConfig = fakerConfig {
            locale = options.locale
        }

        val faker = Faker(fakerConfig)

        val introspector = Introspector(faker)

        val filteredMap = introspector.providerData
            .mapValuesTo(mutableMapOf()) { (_, fpPair) ->
                val (functions, properties) = fpPair
                functions.filter { it.toString().lowercase().contains(name.lowercase()) } to
                    properties.filter { (sub, funcs) ->
                        sub.toString().lowercase().contains(name.lowercase()) ||
                            funcs.any { f -> f.toString().lowercase().contains(name.lowercase()) }
                    }
            }.filterValues { (funcs, subFuncs) ->
                funcs.count() > 0 || subFuncs.isNotEmpty()
            }

        val renderedProviders = filteredMap.map { (provider, fpPair) ->
            val (functions, properties) = fpPair
            renderProvider(options, faker, provider, null, functions, properties)
        }

        val output = Renderer("Faker()", renderedProviders).toString()

        println(output)
    }

    override fun run() {
        printMatchingFunctions()
    }
}
