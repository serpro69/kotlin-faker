package io.github.serpro69.kfaker.app.subcommands

import io.github.serpro69.kfaker.app.KFaker
import picocli.CommandLine

@CommandLine.Command(
    name = "list"
)
object List : SubCommand() {
    @CommandLine.Spec
    override lateinit var spec: CommandLine.Model.CommandSpec

    @CommandLine.ParentCommand
    override lateinit var parent: KFaker

    override fun run() {
        super.run()

        val list = if (verbose) {
            TODO("Not implemented")
        } else {
            renderer.toString()
        }

        println(list)
    }
}
