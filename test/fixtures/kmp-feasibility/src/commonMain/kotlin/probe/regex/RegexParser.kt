package probe.regex

internal sealed class RegexNode(val maxLength: Int, val position: Int) {
    class Text(val value: String, position: Int) : RegexNode(value.length, position)
    class Characters(val values: String, position: Int) : RegexNode(1, position)
    class Sequence(val children: List<RegexNode>, length: Int, position: Int) : RegexNode(length, position)
    class Alternative(val branches: List<RegexNode>, length: Int, position: Int) : RegexNode(length, position)
    class Repetition(val body: RegexNode, val first: Int, val last: Int, length: Int, position: Int) : RegexNode(length, position)
}

internal class RegexGenerationException(val position: Int, reason: String) :
    IllegalArgumentException("Regex position $position: $reason")

private val printable = (32..126).map { it.toChar() }.joinToString("")
private const val digits = "0123456789"
private const val word = digits + "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_"
private const val whitespace = "\t\n\u000b\u000c\r "

/** Parses and bounds the complete grammar before the generator can consume random state. */
internal class RegexParser(private val pattern: String) {
    private var position = 0

    fun parse(): RegexNode {
        if (pattern.length > 16384) throw failure("pattern exceeds 16384 UTF-16 units", 0)
        if (current() == '^') position++
        val root = alternative(0)
        if (current() == '$') position++
        if (position != pattern.length) throw failure("unexpected closing group")
        checkWork(root)
        return root
    }

    private fun checkWork(node: RegexNode): Long {
        val steps = when (node) {
            is RegexNode.Text, is RegexNode.Characters -> 1L
            is RegexNode.Sequence -> {
                var total = 1L
                for (child in node.children) total += checkWork(child)
                total
            }
            is RegexNode.Alternative -> {
                var largest = 0L
                for (branch in node.branches) largest = maxOf(largest, checkWork(branch))
                1L + largest
            }
            is RegexNode.Repetition -> 1L + node.last * checkWork(node.body)
        }
        if (steps > 65536) throw failure("maximum generation work exceeds 65536 node visits", node.position)
        return steps
    }

    private fun alternative(depth: Int): RegexNode {
        val start = position
        val branches = mutableListOf(sequence(depth))
        var length = branches[0].maxLength
        while (current() == '|') {
            position++
            val branch = sequence(depth)
            branches += branch
            length = maxOf(length, branch.maxLength)
        }
        return if (branches.size == 1) branches.single()
        else RegexNode.Alternative(branches, length, start)
    }

    private fun sequence(depth: Int): RegexNode {
        val start = position
        val children = mutableListOf<RegexNode>()
        var length = 0L
        while (position < pattern.length) {
            val char = pattern[position]
            if (char == ')' || char == '|') break
            if (depth == 0 && char == '$' && position == pattern.lastIndex) break
            val node = repeated(atom(depth))
            length += node.maxLength
            checkLength(length, start)
            children += node
        }
        return RegexNode.Sequence(children, length.toInt(), start)
    }

    private fun atom(depth: Int): RegexNode {
        val start = position
        return when (val char = pattern[position++]) {
            '(' -> {
                if (depth >= 64) throw failure("group nesting exceeds 64", start)
                if (current() == '?') {
                    position++
                    if (current() != ':') throw failure("unsupported group construct", start)
                    position++
                }
                val body = alternative(depth + 1)
                // alternative() stops at a closing parenthesis or the end of input.
                if (position == pattern.length) throw failure("unclosed group", start)
                position++
                body
            }
            '[' -> characterClass(start)
            '\\' -> {
                val values = escape(start)
                if (values.length == 1) RegexNode.Text(values, start) else RegexNode.Characters(values, start)
            }
            '.' -> RegexNode.Characters(printable, start)
            '^', '$' -> throw failure("anchors are supported only at the root edges", start)
            '*', '+', '?', '{', '}', ']' -> throw failure("unexpected metacharacter", start)
            else -> {
                val value = if (char.isHighSurrogate()) {
                    val low = current()
                    if (low == null || !low.isLowSurrogate()) throw failure("unpaired surrogate", start)
                    position++
                    char.toString() + low
                } else {
                    if (char.isLowSurrogate()) throw failure("unpaired surrogate", start)
                    char.toString()
                }
                RegexNode.Text(value, start)
            }
        }
    }

    private fun repeated(body: RegexNode): RegexNode {
        val start = position
        val bounds = when (current()) {
            '?' -> { position++; 0 to 1 }
            '*' -> { position++; 0 to 16 }
            '+' -> { position++; 1 to 16 }
            '{' -> {
                position++
                val first = count()
                val last = if (current() == ',') { position++; count() } else first
                if (current() != '}') throw failure("expected closing repetition brace", start)
                position++
                if (first > last) throw failure("reversed repetition bounds", start)
                first to last
            }
            else -> return body
        }
        val length = body.maxLength.toLong() * bounds.second
        checkLength(length, start)
        return RegexNode.Repetition(body, bounds.first, bounds.second, length.toInt(), start)
    }

    private fun count(): Int {
        val start = position
        var count = 0
        while (current() in '0'..'9') {
            count = count * 10 + (pattern[position++] - '0')
            if (count > 4096) throw failure("repetition count exceeds 4096", start)
        }
        if (position == start) throw failure("expected a decimal repetition count", start)
        return count
    }

    private fun characterClass(start: Int): RegexNode {
        val negated = current() == '^'
        if (negated) position++
        val values = linkedSetOf<Char>()
        fun add(chars: Iterable<Char>, at: Int) {
            for (char in chars) {
                if (char.isSurrogate()) throw failure("surrogate ranges are unsupported", at)
                values += char
                if (values.size > 256) throw failure("character class exceeds 256 distinct characters", start)
            }
        }
        while (current() != ']') {
            if (current() == null) throw failure("unclosed character class", start)
            if (pattern.startsWith("&&", position)) throw failure("class intersections are unsupported")
            val atomStart = position
            val first = classAtom(start)
            if (current() == '-' && pattern.getOrNull(position + 1) != ']') {
                position++
                val last = classAtom(start)
                if (first.length != 1 || last.length != 1) throw failure("range endpoints must be literals", atomStart)
                if (first[0] > last[0]) throw failure("descending character range", atomStart)
                add(first[0]..last[0], atomStart)
            } else add(first.asIterable(), atomStart)
        }
        position++
        if (values.isEmpty()) throw failure("empty character class", start)
        val alphabet = if (negated) printable.filter { it !in values } else values.joinToString("")
        if (alphabet.isEmpty()) throw failure("class has no characters in the generation alphabet", start)
        return RegexNode.Characters(alphabet, start)
    }

    private fun classAtom(classStart: Int): String {
        val start = position
        val char = current() ?: throw failure("unclosed character class", classStart)
        position++
        if (char == '\\') return escape(start, inClass = true)
        if (char == '[') throw failure("nested classes are unsupported", start)
        if (char.isSurrogate()) throw failure("supplementary class characters are unsupported", start)
        return char.toString()
    }

    private fun escape(start: Int, inClass: Boolean = false): String {
        val char = current() ?: throw failure("trailing escape", start)
        position++
        return when (char) {
            'd' -> digits
            'D' -> printable.filter { it !in digits }
            'w' -> word
            'W' -> printable.filter { it !in word }
            's' -> whitespace
            'S' -> printable.filter { it !in whitespace }
            't' -> "\t"
            'n' -> "\n"
            'r' -> "\r"
            'f' -> "\u000c"
            'v' -> "\u000b"
            '-' -> if (inClass) "-" else throw failure("escaped hyphen is supported only inside classes", start)
            '\\', '.', '[', ']', '{', '}', '(', ')', '*', '+', '?', '|', '^', '$', '/' -> char.toString()
            else -> throw failure("unsupported escape", start)
        }
    }

    private fun checkLength(length: Long, start: Int) {
        if (length > 4096) throw failure("maximum generated output exceeds 4096 UTF-16 units", start)
    }

    private fun current(): Char? = pattern.getOrNull(position)

    private fun failure(reason: String, at: Int = position): RegexGenerationException =
        RegexGenerationException(at, reason)
}
