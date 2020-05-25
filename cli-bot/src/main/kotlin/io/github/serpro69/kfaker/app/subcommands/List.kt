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
        println("Print list of providers and their functions")
    }
}
