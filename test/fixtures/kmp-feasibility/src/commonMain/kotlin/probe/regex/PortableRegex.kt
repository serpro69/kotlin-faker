package probe.regex

import probe.random.RandomSampling

/** Generation only, with the grammar/limits specified in the Task 3 feasibility evidence. */
internal class PortableRegex(private val random: RandomSampling) {
    fun generate(pattern: String): String {
        val root = RegexParser(pattern).parse()
        return buildString { appendNode(root) }
    }

    private fun StringBuilder.appendNode(node: RegexNode) {
        when (node) {
            is RegexNode.Text -> append(node.value)
            is RegexNode.Characters -> append(node.values[random.nextInt(node.values.length)])
            is RegexNode.Sequence -> node.children.forEach { appendNode(it) }
            is RegexNode.Alternative -> appendNode(node.branches[random.nextInt(node.branches.size)])
            is RegexNode.Repetition -> {
                val count = if (node.first == node.last) node.first else random.intInRange(node.first, node.last)
                repeat(count) { appendNode(node.body) }
            }
        }
    }
}
