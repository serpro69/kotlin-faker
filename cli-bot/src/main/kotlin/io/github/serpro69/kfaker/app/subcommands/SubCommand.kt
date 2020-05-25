package io.github.serpro69.kfaker.app.subcommands

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.app.*
import picocli.*

abstract class SubCommand : Runnable {
    abstract val spec: CommandLine.Model.CommandSpec
    abstract val parent: KFaker
    lateinit var faker: Faker
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
    }
}
