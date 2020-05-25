package io.github.serpro69.kfaker.app.subcommands

import io.github.serpro69.kfaker.app.KFaker
import picocli.CommandLine

@CommandLine.Command(
    name = "lookup"
)
object Lookup : SubCommand() {
    @CommandLine.Spec
    override lateinit var spec: CommandLine.Model.CommandSpec

    @CommandLine.ParentCommand
    override lateinit var parent: KFaker

    @CommandLine.Parameters(index = "0")
    lateinit var functionName: String

    override fun run() {
        super.run()
        println("Print providers with functions matching $functionName")
    }
}
