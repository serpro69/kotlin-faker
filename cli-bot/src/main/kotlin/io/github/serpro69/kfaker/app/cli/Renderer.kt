package io.github.serpro69.kfaker.app.cli

import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.app.subcommands.CommandOptions
import io.github.serpro69.kfaker.provider.Money
import kotlin.reflect.KFunction
import kotlin.reflect.KProperty
import kotlin.system.exitProcess

/**
 * Renders and prints a tree with root (parent) node [name] and a list of [children] nodes.
 *
 * The children node can be an `emptyList` if no children are present on the next level.
 *
 * For example:
 * ```
 * Renderer("parent", listOf(
 *     Renderer("child1", Renderer("grandChild1", emptyList)),
 *     Renderer("child2", emptyList()),
 *     Renderer("child3", Renderer("grandChild3", emptyList))
 * ).toString()
 * ```
 * will print:
 * ```
 * parent
 * ├── child1
 * |   └── grandChild1
 * ├── child2
 * └── child3
 *     └── grandChild3
 * ```
 */
class Renderer(private val name: String, private val children: List<Renderer>) {

    override fun toString(): String {
        val buffer = StringBuilder()
        printTree(buffer, "", "")
        return buffer.toString().trim()
    }

    private fun printTree(buffer: StringBuilder, prefix: String, childrenPrefix: String) {
        buffer.append(prefix)
        buffer.append(name)
        buffer.append('\n')

        children.iterator().withIndex().forEachRemaining {
            if (it.index == children.size - 1) {
                it.value.printTree(buffer, "$childrenPrefix└── ", "$childrenPrefix    ")
            } else {
                it.value.printTree(buffer, "$childrenPrefix├── ", "$childrenPrefix│   ")
            }
        }
    }
}

/**
 * Renders the [provider] and it's [functions] of a [faker] instance and returns as [Renderer].
 */
fun renderProvider(
    options: CommandOptions,
    faker: Faker,
    provider: KProperty<*>,
    functions: Sequence<KFunction<*>>
): Renderer {
    val renderedFunctions = if (options.verbose) {
        functions.map {
            val value = when (it.parameters.size) {
                1 -> it.call(provider.getter.call(faker)).toString()
                2 -> it.call(provider.getter.call(faker), "").toString()
                3 -> it.call(provider.getter.call(faker), "", "").toString()
                else -> {
                    when (it) {
                        Money::amount -> it.call(provider.getter.call(faker), 100_000..1_000_000, true, ",", ".")
                        else -> {
                            println("Could not call the function $it")
                            exitProcess(3)
                        }
                    }
                }
            }

            Renderer("${it.name}() // => $value", emptyList())
        }
    } else {
        functions.asSequence().map { Renderer("${it.name}()", emptyList()) }
    }

    return if (options.javaSyntax) {
        val getterName = "get${provider.name.first().uppercase()}${provider.name.substring(1)}()"
        Renderer(getterName, renderedFunctions.toList())
    } else {
        Renderer(provider.name, renderedFunctions.toList())
    }
}
