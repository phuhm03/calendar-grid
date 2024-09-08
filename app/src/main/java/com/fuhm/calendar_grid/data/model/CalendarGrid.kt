package com.fuhm.calendar_grid.data.model

import java.util.Calendar

data class CalendarGrid(
    val day: Int,
    val month: Int,
    val year: Int
) {
    fun isToday(): Boolean {
        val today = Calendar.getInstance()
        return day == today.get(Calendar.DAY_OF_MONTH) &&
                month == (today.get(Calendar.MONTH) + 1) &&
                year == today.get(Calendar.YEAR)
    }
}