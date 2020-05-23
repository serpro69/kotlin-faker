package io.github.serpro69.kfaker.app.subcommands

import io.github.serpro69.kfaker.*
import io.github.serpro69.kfaker.app.*
import picocli.*

abstract class SubCommand : Runnable {
    abstract val spec: CommandLine.Model.CommandSpec
    abstract val parent: KFaker
    lateinit var faker: Faker

    override fun run() {
        val fakerConfig = FakerConfig.builder().create {
            this.locale = parent.locale
            this.random = java.util.Random(parent.seed)
            this.uniqueGeneratorRetryLimit = parent.retryLimit
        }

        faker = Faker(fakerConfig)
    }
}
