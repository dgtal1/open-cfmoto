package dev.zanderp.opencfmoto

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.Calendar
import java.util.TimeZone

class DashClockTest {
    @Test
    fun millisSinceLocalMidnight_romeEvening() {
        val zone = TimeZone.getTimeZone("Europe/Rome")
        val cal = Calendar.getInstance(zone)
        cal.set(2026, Calendar.AUGUST, 13, 20, 48, 57)
        cal.set(Calendar.MILLISECOND, 276)
        assertEquals(
            (((20L * 60 + 48) * 60 + 57) * 1000) + 276,
            DashClock.millisSinceLocalMidnight(cal.timeInMillis, zone),
        )
    }

    @Test
    fun nameLooksLikeDash_mlnAndSoftAp() {
        assertTrue(DashClock.nameLooksLikeDash("MLN05D250"))
        assertTrue(DashClock.nameLooksLikeDash("MLN_p2p_7017"))
        assertTrue(DashClock.nameLooksLikeDash("ZM_CB42"))
        assertTrue(DashClock.nameLooksLikeDash("VOGE-006348"))
        assertFalse(DashClock.nameLooksLikeDash("WH-1000XM5"))
        assertFalse(DashClock.nameLooksLikeDash("Galaxy Watch"))
        assertFalse(DashClock.nameLooksLikeDash(null))
    }
}
