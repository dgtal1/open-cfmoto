// SPDX-License-Identifier: AGPL-3.0-or-later
// Copyright (C) 2026 Alexandru <https://alexandru.rocks> and the OpenCfMoto contributors.
// Part of OpenCfMoto. Free software under the GNU AGPL v3 or later; see LICENSE and NOTICE.
package dev.zanderp.opencfmoto

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

/**
 * Body for `0x10451` — reply to bike `ECP_C2P_QUERY_TIME` (`0x10450`).
 *
 * Empty `0x10451` is treated as epoch on Voge / QJ / Griffin / X-Cape (cluster → 00:00 / 1970).
 * Official Carbit Ride answers with JSON:
 *   `{"time": <epochMillis>, "dateTime": "dd.MM.yyyy HH:mm:ss:SSS"}`
 * (day-first, colon before milliseconds — not the `0x10600` `yyyy-MM-dd` stamp).
 */
internal object HuQueryTime {
    private val dateTimeFmt = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.US)

    data class Ack(val payload: ByteArray, val dateTime: String, val timeMillis: Long)

    fun ack(nowMillis: Long = System.currentTimeMillis(), zone: TimeZone = TimeZone.getDefault()): Ack {
        val dateTime = synchronized(dateTimeFmt) {
            dateTimeFmt.timeZone = zone
            String.format(Locale.US, "%s:%03d", dateTimeFmt.format(Date(nowMillis)), (nowMillis % 1000L).toInt())
        }
        val json = "{\"time\":$nowMillis,\"dateTime\":\"$dateTime\"}"
        return Ack(json.toByteArray(Charsets.UTF_8), dateTime, nowMillis)
    }
}
