package dev.zanderp.opencfmoto

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.TimeZone

class HuQueryTimeTest {
    @Test
    fun ack_isCarbitJson_dayFirstWithMillis() {
        val zone = TimeZone.getTimeZone("Europe/Rome")
        val now = 1_786_272_219_244L
        val ack = HuQueryTime.ack(now, zone)
        val body = String(ack.payload, Charsets.UTF_8)
        assertEquals("{\"time\":$now,\"dateTime\":\"09.08.2026 12:43:39:244\"}", body)
        assertEquals("09.08.2026 12:43:39:244", ack.dateTime)
        assertTrue(ack.payload.isNotEmpty())
    }
}
