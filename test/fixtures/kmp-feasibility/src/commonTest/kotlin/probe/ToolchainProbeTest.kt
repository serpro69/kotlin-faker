package probe

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.time.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class ToolchainProbeTest {
    @Test
    fun jsonTreePreservesUnicodeOrderAndScalarKinds() {
        val tree = Json.parseToJsonElement(
            """{"values":["東京","Åse",7,"007",null]}"""
        ).jsonObject.getValue("values").jsonArray
        assertEquals(listOf("東京", "Åse"), tree.take(2).map { it.jsonPrimitive.content })
        assertEquals(7, tree[2].jsonPrimitive.int)
        assertEquals(false, tree[2].jsonPrimitive.isString)
        assertEquals("007", tree[3].jsonPrimitive.content)
        assertEquals(true, tree[3].jsonPrimitive.isString)
        assertEquals(JsonNull, tree[4])
    }

    @Test
    fun dateAndInstantApisPreserveLeapDayAndFixedOffset() {
        val leapDay = LocalDate.parse("2024-02-29")
        val instant = Instant.parse("2024-02-29T23:30:00Z")
        assertEquals("2024-02-29", leapDay.toString())
        assertEquals(
            "2024-03-01T01:30",
            instant.toLocalDateTime(TimeZone.of("UTC+02:00")).toString(),
        )
    }

    @Test
    fun invalidInputsFailInsteadOfBeingSilentlyAdjusted() {
        assertFailsWith<IllegalArgumentException> { LocalDate.parse("2023-02-29") }
        assertFailsWith<IllegalArgumentException> { Json.parseToJsonElement("{") }
    }
}
