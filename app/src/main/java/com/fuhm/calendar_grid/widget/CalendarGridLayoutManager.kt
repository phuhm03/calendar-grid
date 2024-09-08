package com.fuhm.calendar_grid.widget

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager

class CalendarGridLayoutManager(context: Context, spanCount: Int) : GridLayoutManager(context, spanCount) {
    override fun canScrollVertically(): Boolean {
        return false
    }
}
