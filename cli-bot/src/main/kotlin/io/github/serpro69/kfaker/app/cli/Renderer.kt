package io.github.serpro69.kfaker.app.cli

import io.github.serpro69.kfaker.*

/**
 * Renders and prints a tree.
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
