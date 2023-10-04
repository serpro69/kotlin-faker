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
    subProvider: KProperty<*>?,
    functions: Sequence<KFunction<*>>,
    properties: Map<KProperty<*>, Sequence<KFunction<*>>>,
): Renderer {
    val renderedFunctions = if (options.verbose) {
        val instance = if (subProvider != null) {
            subProvider.getter.call(provider.getter.call(faker))
        } else {
            provider.getter.call(faker)
        }
        functions.map {
            val value = when (it.parameters.size) {
                1 -> it.call(instance).toString()
                2 -> {
                    if (it.parameters[1].isOptional) { // optional params are enum typed (see functions in Dune, Finance or Tron, for example)
                        it.callBy(mapOf(it.parameters[0] to instance)).toString()
                    } else it.call(instance, "").toString()
                }
                3 -> it.call(instance, "", "").toString()
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
        functions.map { Renderer("${it.name}()", emptyList()) }
    }
    val subFunctions = properties.map { (sub, funcs) ->
        renderProvider(options, faker, provider, sub, funcs, emptyMap())
    }

    val name = subProvider?.name ?: provider.name
    val allFunctions = renderedFunctions.toList().plus(subFunctions)

    return if (options.javaSyntax) {
        val getterName = "get${name.first().uppercase()}${name.substring(1)}()"
        Renderer(getterName, allFunctions)
    } else {
        Renderer(name, allFunctions)
    }
}
