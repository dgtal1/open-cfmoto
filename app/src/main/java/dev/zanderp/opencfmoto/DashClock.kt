// SPDX-License-Identifier: AGPL-3.0-or-later
// Copyright (C) 2026 Alexandru <https://alexandru.rocks> and the OpenCfMoto contributors.
// Part of OpenCfMoto. Free software under the GNU AGPL v3 or later; see LICENSE and NOTICE.
package dev.zanderp.opencfmoto

import java.util.Calendar
import java.util.TimeZone

/** Shared dash-clock helpers (CLIENT_INFO `currentHUTime` + BLE name matching). */
internal object DashClock {
    fun millisSinceLocalMidnight(
        nowMillis: Long = System.currentTimeMillis(),
        zone: TimeZone = TimeZone.getDefault(),
    ): Long {
        val cal = Calendar.getInstance(zone)
        cal.timeInMillis = nowMillis
        val h = cal.get(Calendar.HOUR_OF_DAY)
        val m = cal.get(Calendar.MINUTE)
        val s = cal.get(Calendar.SECOND)
        val ms = cal.get(Calendar.MILLISECOND)
        return (((h * 60L + m) * 60L + s) * 1000L) + ms
    }

    fun nameLooksLikeDash(name: String?): Boolean {
        val n = name?.trim().orEmpty()
        if (n.isEmpty()) return false
        val u = n.uppercase()
        return DASH_NAME_MARKERS.any { u.contains(it) }
    }

    private val DASH_NAME_MARKERS = listOf(
        "MLN", "ZM_", "ZM-", "VOGE", "CFMOTO", "QJ", "GRIFFIN", "MORINI",
        "XCAPE", "X-CAPE", "CARBIT", "EASYCONN", "YUNMO", "ALLTR",
        "P2P", "SOFTAP",
    )
}
