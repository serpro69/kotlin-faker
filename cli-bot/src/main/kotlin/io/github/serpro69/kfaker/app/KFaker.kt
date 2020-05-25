package io.github.serpro69.kfaker.app

import io.github.serpro69.kfaker.app.subcommands.List
import io.github.serpro69.kfaker.app.subcommands.Lookup
import picocli.CommandLine
import kotlin.system.exitProcess

@CommandLine.Command(
    name = "kFaker",
    description = ["Generates realistic looking fake data"],
    version = [
        "kFaker 1.1.1",
        "Built with picocli ${CommandLine.VERSION}",
        "JVM: \${java.version} (\${java.vendor} \${java.vm.name} \${java.vm.version})",
        "OS: \${os.name} \${os.version} \${os.arch}"
    ],
    subcommands = [
        List::class,
        Lookup::class
    ]
)
object KFaker : Runnable {
    @CommandLine.Option(
        names = ["-l", "--locale"],
        description = ["dictionary to use when generating data", "default: 'en'"],
        required = false
    )
    internal var locale: String = "en"

    @CommandLine.Option(names = ["-h", "--help"], usageHelp = true, description = ["display this help message"])
    private var usageHelpRequested: Boolean = false

    @CommandLine.Option(names = ["--version"], versionHelp = true, description = ["display version info"])
    private var versionHelpRequested: Boolean = false

    @CommandLine.Spec
    lateinit var spec: CommandLine.Model.CommandSpec

    override fun run() {
        throw CommandLine.ParameterException(spec.commandLine(), "Specify parameter")
    }
}

fun main(args: Array<String>) {
    val cli = CommandLine(KFaker)
    exitProcess(cli.execute(*args))
}

