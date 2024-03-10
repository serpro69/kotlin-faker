package io.github.serpro69.kfaker.app

import io.github.serpro69.kfaker.app.subcommands.List
import io.github.serpro69.kfaker.app.subcommands.Lookup
import picocli.CommandLine
import kotlin.system.exitProcess

@CommandLine.Command(
    name = "faker-bot",
    description = [
        "helps to quickly find required faker functionality",
        "see https://github.com/serpro69/kotlin-faker/README.md for more installation and usage examples"
    ],
    version = [
        "faker: 0.0.0", // 0.0.0 is a placeholder that will be temporarily replaced during compilation
        "faker-bot: 0.0.0",
        "Built with picocli ${CommandLine.VERSION}",
        "JVM: \${java.version} (\${java.vendor} \${java.vm.name} \${java.vm.version})",
        "OS: \${os.name} \${os.version} \${os.arch}"
    ],
    synopsisHeading = "Usage:%n",
    descriptionHeading = "%nDescription:%n",
    parameterListHeading = "%nParameters:%n",
    optionListHeading = "%nOptions:%n",
    commandListHeading = "%nCommands:%n",
    subcommands = [
        List::class,
        Lookup::class
    ]
)
object KFaker : Runnable {

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

