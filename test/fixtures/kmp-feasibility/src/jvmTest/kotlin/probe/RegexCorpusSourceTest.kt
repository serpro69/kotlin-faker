package probe

import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RegexCorpusSourceTest {
    @Test
    fun commonCorpusMatchesEveryTaskOneInventoryOccurrence() {
        val inventory = File(requireNotNull(System.getProperty("regex.inventory")))
        val actual = regexCorpus.associateBy { it.source to it.key }
        assertEquals(regexCorpus.size, actual.size, "duplicate corpus location")
        val rows = inventory.readLines().drop(1)
        assertEquals(rows.size, actual.size)
        val expectedLocations = mutableSetOf<Pair<String, String>>()
        for (line in rows) {
            // Task 1's TSV escapes backslash, tab and line breaks; decode exactly once.
            val cells = line.split('\t').map { value ->
                buildString {
                    var index = 0
                    while (index < value.length) {
                        val char = value[index++]
                        if (char != '\\') append(char)
                        else append(when (val escaped = value[index++]) {
                            '\\' -> '\\'
                            't' -> '\t'
                            'n' -> '\n'
                            'r' -> '\r'
                            else -> error("Unknown inventory escape: $escaped")
                        })
                    }
                }
            }
            assertEquals(4, cells.size)
            val expected = RegexInput(cells[0], cells[1], cells[2], cells[3])
            assertTrue(expectedLocations.add(expected.source to expected.key), "duplicate inventory location")
            assertEquals(expected, actual[expected.source to expected.key], expected.source + ":" + expected.key)
        }
        assertEquals(actual.keys, expectedLocations)
    }
}
