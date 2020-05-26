package io.github.serpro69.kfaker.app.subcommands

import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.FakerConfig
import io.github.serpro69.kfaker.app.KFaker
import io.github.serpro69.kfaker.app.cli.Introspector
import io.github.serpro69.kfaker.app.cli.Renderer
import io.github.serpro69.kfaker.create
import picocli.CommandLine

abstract class SubCommand : Runnable {
    abstract val spec: CommandLine.Model.CommandSpec
    abstract val parent: KFaker
    lateinit var faker: Faker
        private set
    private lateinit var introspector: Introspector
    lateinit var renderer: Renderer
        private set

    @CommandLine.Option(
        names = ["-V", "--verbose"],
        description = ["Adds a sample value to the output"]
    )
    protected var verbose: Boolean = false
        private set

    override fun run() {
        val fakerConfig = FakerConfig.builder().create {
            this.locale = parent.locale
        }

        faker = Faker(fakerConfig)
        introspector = Introspector(faker)

        val providers = introspector.providerFunctions.map { (k, v) ->
            val functions = v.map {
                Renderer("${it.name}()", emptyList())
            }

            Renderer(k.name, functions)
        }

        renderer = Renderer("Faker()", providers)
    }
}
