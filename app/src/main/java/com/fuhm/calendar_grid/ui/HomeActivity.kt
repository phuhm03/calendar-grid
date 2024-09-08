package com.fuhm.calendar_grid.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fuhm.calendar_grid.adapter.CalendarGridAdapter
import com.fuhm.calendar_grid.widget.CalendarGridLayoutManager
import com.fuhm.calendar_grid.databinding.ActivityHomeBinding
import com.fuhm.calendar_grid.data.model.CalendarGrid
import java.util.Calendar
import java.util.Locale

class HomeActivity : AppCompatActivity() {
    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding!!
    private var calendarGridAdapter: CalendarGridAdapter? = null
    private var calendar = Calendar.getInstance()
    companion object {
        private const val CALENDAR_SPAN_COUNT = 7
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        handleEvents()
    }

    private fun initViews() {
        initRecyclerViewCalendar()
        initTextViewMonth()
    }

    @SuppressLint("SetTextI18n")
    private fun initTextViewMonth() {
        val month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
        val year = calendar.get(Calendar.YEAR)
        binding.tvMonth.text = "$month $year"
    }

    private fun initRecyclerViewCalendar() {
        calendarGridAdapter = CalendarGridAdapter().apply {
            submitList(generateCalendarGrids(calendar))
        }
        binding.rcvCalendars.apply {
            layoutManager = CalendarGridLayoutManager(this@HomeActivity, CALENDAR_SPAN_COUNT)
            itemAnimator = null
            adapter = calendarGridAdapter
        }
    }

    private fun generateCalendarGrids(mCalendar: Calendar): List<CalendarGrid> {
        val calendarGrids = mutableListOf<CalendarGrid>()

        // Khởi tạo Calendar với tháng và năm từ mCalendar
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.MONTH, mCalendar.get(Calendar.MONTH)) // Sử dụng tháng từ mCalendar
        calendar.set(Calendar.YEAR, mCalendar.get(Calendar.YEAR)) // Sử dụng năm từ mCalendar
        val firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

        // Thêm các ô trống trước ngày đầu tiên của tháng
        for (i in 1 until firstDayOfWeek) {
            calendarGrids.add(CalendarGrid(0, 0, 0))
        }

        // Thêm các ngày của tháng hiện tại
        val maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        for (day in 1..maxDay) {
            calendarGrids.add(
                CalendarGrid(day,
                    calendar.get(Calendar.MONTH) + 1, // Tháng bắt đầu từ 0
                    calendar.get(Calendar.YEAR)
                )
            )
        }

        // Xác định số ô cần thêm để hoàn thành lưới
        val totalCells = if (calendarGrids.size > 35) 42 else 35
        while (calendarGrids.size < totalCells) {
            calendarGrids.add(CalendarGrid(0, 0, 0))
        }

        return calendarGrids
    }

    private fun handleEvents() {
        binding.btnNextCal.setOnClickListener {
            calendar.add(Calendar.MONTH, 1)
            initTextViewMonth()
            initRecyclerViewCalendar()
        }

        binding.btnPreviousCal.setOnClickListener {
            calendar.add(Calendar.MONTH, -1)
            initTextViewMonth()
            initRecyclerViewCalendar()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        calendarGridAdapter = null
    }
}