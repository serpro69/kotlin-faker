package io.github.serpro69.kfaker.app.subcommands

import picocli.CommandLine
import io.github.serpro69.kfaker.app.KFaker

/**
 * Reusable options for [KFaker] commands
 */
@CommandLine.Command(
    synopsisHeading = "Usage:%n",
    descriptionHeading = "%nDescription:%n",
    parameterListHeading = "%nParameters:%n",
    optionListHeading = "%nOptions:%n",
    commandListHeading = "%nCommands:%n"
)
class CommandOptions {

    @CommandLine.Option(
        names = ["-l", "--locale"],
        description = ["dictionary to use when generating data", "default: 'en'"],
        required = false
    )
    var locale: String = "en"

    @CommandLine.Option(
        names = ["--java-syntax"],
        description = ["display syntax for java instead of kotlin"],
        required = false
    )
    var javaSyntax: Boolean = false

    @CommandLine.Option(
        names = ["-v", "--verbose"],
        description = ["adds a sample value to the output"],
        required = false
    )
    var verbose: Boolean = false
}
